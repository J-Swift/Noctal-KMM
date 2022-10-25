package com.radreichley.noctal.stories

import com.radreichley.noctal.base.sharedviewmodel.CommonFlow
import com.radreichley.noctal.base.sharedviewmodel.SharedViewModel
import com.radreichley.noctal.base.sharedviewmodel.asCommonFlow
import kotlinx.coroutines.flow.combine

data class StoryWithMeta(val story: Story, val meta: StoryMeta?)

class SharedStoriesViewModel(private val storiesRepository: StoriesRepository) : SharedViewModel {
    fun observeStories(): CommonFlow<List<StoryWithMeta>> {
        return storiesRepository.getStories()
            .combine(storiesRepository.getMetas()) { stories, metas ->
                val lookup = mutableMapOf<String, StoryMeta>()
                metas.forEach { lookup[it.id] = it }

                stories.map { StoryWithMeta(it, lookup[it.id]) }
            }
            .asCommonFlow()
    }
}