package com.radreichley.noctal.android.stories

import androidx.lifecycle.ViewModel
import com.radreichley.noctal.base.db.Database
import com.radreichley.noctal.base.sharedviewmodel.CommonFlow
import com.radreichley.noctal.module.HN.HNApi.HNApiMock
import com.radreichley.noctal.module.meta_fetcher.meta_fetcher.MetaFetcher
import com.radreichley.noctal.stories.SharedStoriesViewModel
import com.radreichley.noctal.stories.StoriesRepository
import com.radreichley.noctal.stories.StoryWithMeta

class AndroidStoriesViewModel : ViewModel() {
    private val vm =
        SharedStoriesViewModel(StoriesRepository(Database(), HNApiMock(), MetaFetcher()))

    fun observeStories(): CommonFlow<List<StoryWithMeta>> = vm.observeStories()

    override fun onCleared() {
        vm.close()
    }
}
