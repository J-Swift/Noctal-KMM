package com.radreichley.noctal.android.stories

import android.content.res.Configuration
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.toPlatform
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.base.db.Database
import com.radreichley.noctal.base.db.Story
import com.radreichley.noctal.module.HN.HNApi.HNApiMock
import com.radreichley.noctal.module.meta_fetcher.meta_fetcher.MetaFetcher
import com.radreichley.noctal.stories.StoriesService
import com.radreichley.noctal.stories.StoryCellConfig

@Composable
fun StoryView(modifier: Modifier = Modifier, viewModel: AndroidStoriesViewModel = viewModel()) {
    StoryView_Content(
        stories = viewModel.observeStories().collectAsState(listOf()).value,
        modifier
    )
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
        var selectedIdx by remember { mutableStateOf(-1) }
        val selectionColor =
            (if (isSystemInDarkTheme()) StoryCellConfig.Styling.CellHighlightDk else StoryCellConfig.Styling.CellHighlightLt).toPlatform()

        CompositionLocalProvider(
            LocalIndication provides rememberRipple(color = selectionColor)
        ) {
            LazyColumn {
                items(stories.size, key = { stories[it].id }) {
                    Column(
                        modifier = Modifier.selectable(
                            selected = selectedIdx == it,
                            onClick = { println("clicked $it"); selectedIdx = it })
                    ) {
                        StoryCell(stories[it], it + 1, isSelected = selectedIdx == it)

                        Divider()
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoryView_Preview() {
    val theme = if (isSystemInDarkTheme()) DarkTheme() else LightTheme()

    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        StoryView_Content(StoriesService.mockStories)
    }
}