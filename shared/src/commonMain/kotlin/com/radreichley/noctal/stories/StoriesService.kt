package com.radreichley.noctal.stories
//
//import com.radreichley.noctal.base.db.Database
//import com.radreichley.noctal.base.db.Story
//import com.radreichley.noctal.module.HN.HNApi.HNApiMock
//import com.radreichley.noctal.module.HN.HNApi.IHNApi
//import com.radreichley.noctal.module.HN.models.StoryDto
//import com.radreichley.noctal.module.HN.models.StoryType
//import com.radreichley.noctal.module.meta_fetcher.IMetaFetcher
//import io.realm.kotlin.internal.platform.freeze
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class StoriesService(
//    private val db: Database, private val hnApi: IHNApi, private val metaFetcher: IMetaFetcher
//) {
//    companion object {
//        val mockStories: List<Story> by lazy {
//            HNApiMock.stories.map { dto ->
//                Story(dto.id).also {
//                    updateItem(it, dto)
//                }
//            }
//        }
//
//        private fun updateItem(item: Story, dto: StoryDto) {
//            val urlPath = when {
//                dto.urlPath is String -> dto.urlPath
//                dto.typeOfStory == StoryType.Story -> "https://news.ycombinator.com"
//                else -> "<unknown>"
//            }
//
//            item.apply {
//                url = urlPath
//                title = dto.title
//                submitter = dto.author
////            timeAgo = dto.
//                timeAgo = "1m ago"
//                score = dto.score
//                numComments = dto.numComments
//                storyText = dto.storyText
//            }
//        }
//    }
//
//    val stories
//        get() = db.realm.query(Story::class).find().asFlow().map { it.list }
//
//    init {
//        CoroutineScope(Dispatchers.Default).launch {
//            fetchStories()
//        }
//    }
//
//    private suspend fun fetchStories() {
//        val stories = hnApi.getStoriesAsync()
//
//
////        withContext(Dispatchers.)
//        val storyIds = db.realm.writeBlocking {
//            stories.map { storyDto ->
//                val item =
//                    db.realm.query(Story::class, "id == '${storyDto.id}'").first().find() ?: Story(
//                        storyDto.id
//                    )
//                updateItem(item, storyDto)
//
//                return@map copyToRealm(item).id
//            }
//        }
//
//        items.forEach { getMeta(it) }
//    }
//
//    private suspend fun getMeta(storyId: String) {
//        val story = db.realm.query(Story::class, "id == '${storyId}'").first().find() ?: return
//
//        if (story.favIconPath != null && story.imagePath != null) {
//            return
//        }
//
//        println("JIMMY getmeta for ${story.id}")
//
//        val meta = metaFetcher.getMetaAsync(story.url)
//        db.realm.writeBlocking {
//            val updated = findLatest(story)?.apply {
//                favIconPath = meta.favIconPath
//                imagePath = meta.ogImagePath
//            }
//
//            if (updated != null) {
//                copyToRealm(updated)
//            }
//        }
//    }
//}
