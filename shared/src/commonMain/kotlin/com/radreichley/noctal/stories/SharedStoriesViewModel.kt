package com.radreichley.noctal.stories

import com.radreichley.noctal.base.db.Story
import com.radreichley.noctal.base.sharedviewmodel.CommonFlow
import com.radreichley.noctal.base.sharedviewmodel.SharedViewModel
import com.radreichley.noctal.base.sharedviewmodel.asCommonFlow

class SharedStoriesViewModel(private val storiesSvc: StoriesService) : SharedViewModel {
    fun observeStories(): CommonFlow<List<Story>> {
        return storiesSvc.stories
            .asCommonFlow()
    }
}