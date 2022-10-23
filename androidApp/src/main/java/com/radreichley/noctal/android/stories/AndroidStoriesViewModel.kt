package com.radreichley.noctal.android.stories

import androidx.lifecycle.ViewModel
import com.radreichley.noctal.base.db.Database
import com.radreichley.noctal.base.db.Story
import com.radreichley.noctal.base.sharedviewmodel.CommonFlow
import com.radreichley.noctal.module.HN.HNApi.HNApiMock
import com.radreichley.noctal.module.meta_fetcher.meta_fetcher.MetaFetcher
import com.radreichley.noctal.stories.SharedStoriesViewModel
import com.radreichley.noctal.stories.StoriesService

class AndroidStoriesViewModel : ViewModel() {
    private val vm = SharedStoriesViewModel(StoriesService(Database(), HNApiMock(), MetaFetcher()))
    fun observeStories(): CommonFlow<List<Story>> = vm.observeStories()

    override fun onCleared() {
        vm.close()
    }
}