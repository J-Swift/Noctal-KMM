package com.radreichley.noctal.android.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.PreviewThemeProvider
import com.radreichley.noctal.android.base.toPlatform
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme

@Composable
fun StoryView(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .background(
                LocalNoctalTheme.current.backgroundColor.toPlatform()
            )
    ) {
        LazyColumn {
            items(10) {
                Column {
                    StoryCell()

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