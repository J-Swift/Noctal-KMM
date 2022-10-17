package com.radreichley.noctal.android.stories

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.toPlatform
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.module.HN.HNApi.HNApiMock
import com.radreichley.noctal.module.HN.models.Story
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Composable
fun StoryView(modifier: Modifier = Modifier, viewModel: StoriesViewModel = viewModel() ) {
    StoryView_Content(stories = viewModel.stories.collectAsState().value, modifier)
}

@Composable
private fun StoryView_Content(stories: List<Story>, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .background(
                LocalNoctalTheme.current.backgroundColor.toPlatform()
            )
    ) {
        LazyColumn {
            items(stories.size, key = { stories[it].id }) {
                Column {
                    StoryCell(stories[it], it + 1)

                    Divider()
                }
            }
        }
    }
}

class StoriesViewModel : ViewModel() {
    private val _stories = MutableStateFlow<List<Story>>(listOf())
    val stories = _stories.asStateFlow()

    init {
        viewModelScope.launch {
            val newStories = HNApiMock().getStoriesAsync()
            _stories.value = newStories
        }
    }
}

@Preview(showSystemUi = true, group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoryView_Preview() {
    val theme = if (isSystemInDarkTheme()) DarkTheme() else LightTheme()

    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        StoryView_Content(HNApiMock.stories)
    }
}