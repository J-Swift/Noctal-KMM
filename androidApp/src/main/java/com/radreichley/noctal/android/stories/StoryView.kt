package com.radreichley.noctal.android.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.PreviewThemeProvider
import com.radreichley.noctal.android.base.toPlatform
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.module.HN.HNApi.HNApiMock
import com.radreichley.noctal.module.HN.models.Story

@Composable
fun StoryView(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val (loadResult, setLoadResult) = remember { mutableStateOf<List<Story>>(listOf()) }

    LaunchedEffect(true) {
        setLoadResult(HNApiMock().getStoriesAsync())
    }

    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .background(
                LocalNoctalTheme.current.backgroundColor.toPlatform()
            )
    ) {
        LazyColumn {
            items(loadResult.size, key = { loadResult[it].id }) {
                Column {
                    StoryCell(loadResult[it])

                    Divider()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun StoryView_Preview(
    @PreviewParameter(PreviewThemeProvider::class) useDarkTheme: Boolean
) {
    val theme = if (useDarkTheme) DarkTheme() else LightTheme()
    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        StoryView()
    }
}