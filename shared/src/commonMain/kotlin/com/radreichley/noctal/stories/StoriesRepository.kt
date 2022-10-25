package com.radreichley.noctal.stories

import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromStringOrNull
import com.radreichley.noctal.base.db.Database
import com.radreichley.noctal.base.db.StoryDao
import com.radreichley.noctal.base.db.StoryMetaDao
import com.radreichley.noctal.module.HN.HNApi.HNApiMock
import com.radreichley.noctal.module.HN.HNApi.IHNApi
import com.radreichley.noctal.module.HN.models.StoryDto
import com.radreichley.noctal.module.HN.models.StoryType
import com.radreichley.noctal.module.meta_fetcher.IMetaFetcher
import com.radreichley.noctal.module.meta_fetcher.MetaResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

interface IStoriesRepository {
    fun getStories(refresh: Boolean = false): Flow<List<Story>>
    fun getMetas(): Flow<List<StoryMeta>>
}

class StoriesRepositoryMock : IStoriesRepository {
    override fun getStories(refresh: Boolean): Flow<List<Story>> {
        val res = HNApiMock.stories.map { dto ->
            dto.asDao().asExternal()
        }

        return flowOf(res)
    }

    override fun getMetas(): Flow<List<StoryMeta>> {
        // TODO(jpr): implement
        return flowOf(listOf())
    }
}

class StoriesRepository(
    private val db: Database,
    private val api: IHNApi,
    private val metaFetcher: IMetaFetcher,
//    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    // TODO(jpr): DI for ios
//    private val externalScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : IStoriesRepository {
    private val externalScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun getStories(refresh: Boolean): Flow<List<Story>> {
        val allStories = db.realm.query(StoryDao::class).find()

        if (allStories.isEmpty() || refresh) {
            refreshStories()
        }

        return allStories.asFlow()
            .map { it.list.map(StoryDao::asExternal) }
    }

    override fun getMetas(): Flow<List<StoryMeta>> {
        val items = db.realm.query(StoryMetaDao::class).find()

        return items.asFlow()
            .map { it.list.map(StoryMetaDao::asExternal) }
    }

    private fun refreshStories() {
        externalScope.async {
            val dtos = api.getStoriesAsync()

            val stories = async {
                db.realm.writeBlocking {
                    dtos.forEach { dto ->
                        val dao = dto.asDao()
                        copyToRealm(dao)
                    }
                }
                println("JIMMY finished writing stories")
            }

            val metas = async {
                dtos.forEach { dto ->
                    val item = db.realm.query(StoryMetaDao::class, "it == '$dto.id'").first().find()
                    if (item == null || (item.favIconPath == null && item.imagePath == null)) {
                        val meta = metaFetcher.getMetaAsync(dto.asDao().url)
                        db.realm.write {
                            val dao = meta.asDao(dto.id)
                            copyToRealm(dao)
                        }
                        println("JIMMY wrote meta [${dto.id}]")
                    }
                }
                println("JIMMY finished writing metas")
            }

            awaitAll(stories, metas)
        }
    }

    private fun updateItem(dao: StoryDao, dto: StoryDto) {
        val urlPath = when {
            dto.urlPath is String -> dto.urlPath
            dto.typeOfStory == StoryType.Story -> "https://news.ycombinator.com"
            else -> "<unknown>"
        }

        dao.apply {
            url = urlPath
            title = dto.title
            submitter = dto.author
            createdAt = dto.createdAt
            score = dto.score
            numComments = dto.numComments
            storyText = dto.storyText
        }
    }
}

private fun StoryDao.asExternal(): Story {
    return Story(
        id = this.id,
        url = this.url,
        title = this.title,
        submitter = this.submitter,
        createdAt = this.createdAt,
        score = this.score,
        numComments = this.numComments,
        storyText = this.storyText
    )
}

private fun StoryDto.asDao(): StoryDao {
    val urlPath = when {
        urlPath is String -> urlPath
        typeOfStory == StoryType.Story -> "https://news.ycombinator.com"
        else -> "<unknown>"
    }

    return StoryDao().also {
        it.id = id
        it.url = urlPath
        it.title = title
        it.submitter = author
        it.score = score
        it.numComments = numComments
        it.storyText = storyText
    }
}

private fun MetaResult.asDao(id: String): StoryMetaDao {
    return StoryMetaDao(id).also {
        it.favIconPath = favIconPath
        it.imagePath = ogImagePath
    }
}

data class Story(
    val id: String,
    val url: String,
    val title: String,
    val submitter: String,
    val createdAt: Instant,
    val score: Int,
    val numComments: Int,
    val storyText: String?
) {
    val displayUrl: String? by lazy {
        val dVal = url

        var host = urlHost ?: return@lazy dVal

        if (host.startsWith("www.")) {
            host = host.substring(4)
        }

        host
    }

    val placeholderLetter: String? by lazy {
        val dVal = null

        val host = urlHost ?: return@lazy dVal
        val parts = host.split(".")
        parts[parts.count() - 2][0].toString().uppercase()
    }

    private val urlHost: String? by lazy {
        val dVal = null

        url ?: return@lazy dVal
        val uri = Uri.fromStringOrNull(url) ?: return@lazy dVal

        uri.host ?: dVal
    }

//    private string ToTimeAgo(DateTimeOffset date)
//    {
//        var delta = DateTimeOffset.UtcNow.Subtract(date);
//
//        if (delta.TotalSeconds < 60)
//        {
//            return "Just now";
//        }
//
//        if (delta.TotalMinutes < 60)
//        {
//            return $"{(int)delta.TotalMinutes}m ago";
//        }
//
//        if (delta.TotalHours < 24)
//        {
//            return $"{(int)delta.TotalHours}h ago";
//        }
//
//        if (delta.TotalDays < 7)
//        {
//            return $"{(int)delta.TotalDays}d ago";
//        }
//
//        return date.ToString("MMMM d, yyyy");
//    }
}

data class StoryMeta(
    val id: String,
    val favIconPath: String?,
    val imagePath: String?
)

private fun StoryMetaDao.asExternal(): StoryMeta {
    return StoryMeta(
        id = this.id,
        favIconPath = this.favIconPath,
        imagePath = this.imagePath
    )
}

val previewStories = HNApiMock.Companion.stories.map { dto ->
    dto.asDao().asExternal()
}

val previewStoryMetas = listOf(
    StoryMeta(id = previewStories[0].id, favIconPath = null, imagePath = null)
)
