package com.radreichley.noctal.android.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radreichley.noctal.android.base.DEBUG_placeholder
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.PreviewThemeProvider
import com.radreichley.noctal.android.base.toPlatform
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.module.HN.HNApi.HNApiMock
import com.radreichley.noctal.module.HN.models.Story
import com.radreichley.noctal.stories.StoryCellConfig
import com.radreichley.noctal.base.theming.Color as NoctalColor

private val debugColor = NoctalColor.DEBUG_placeholder()
private val dims = StoryCellConfig.Dims
private val styles = StoryCellConfig.Styling

@Composable
fun StoryCell(story: Story) {
    Row(
        modifier = Modifier
            .background(LocalNoctalTheme.current.backgroundColor.toPlatform())
            .padding(
                horizontal = dims.DimHPadding.dp,
                vertical = dims.DimVPadding.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = dims.DimHPadding.dp)
                .size(dims.DimImg.dp, dims.DimImg.dp)
                .background(debugColor, shape = RoundedCornerShape(dims.DimImgRadius.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(story.placeholderLetter ?: "Y", color = Color.White, fontSize = styles.FontSizePlaceholder.sp)
        }

        Column(
            modifier = Modifier.weight(1.0f),
            verticalArrangement = Arrangement.spacedBy(dims.DimVPadding.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dims.DimHPaddingRow.dp)
            ) {
                StoryLabel("1.")
                Box(
                    modifier = Modifier
                        .size(dims.DimImgFavicon.dp, dims.DimImgFavicon.dp)
                        .background(debugColor, shape = RoundedCornerShape(50))
                )
                // TODO(jpr): autosizing text
                StoryLabel(
                    story.displayUrl ?: " ",
                    textColor = LocalNoctalTheme.current.primaryColor.toPlatform()
                )
            }

            StoryLabel(story.title, fontSize = styles.FontSizeTitle, lineLimit = null)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dims.DimHPaddingRow.dp)
            ) {
                StoryLabel(story.author)
                StoryLabel("•")
                StoryLabel("4h ago")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dims.DimHPaddingRow.dp)
            ) {
                StoryLabel("↑")
                StoryLabel(story.score.toString())
                StoryLabel("•")
                StoryLabel("${story.numComments} comments")
            }
        }
    }
}

@Composable
fun StoryLabel(
    text: String,
    textColor: Color = LocalNoctalTheme.current.onBackgroundColor.toPlatform(),
    fontSize: Double = styles.FontSizeDefault,
    lineLimit: Int? = 1
) {
    Text(
        text,
        color = textColor,
        fontSize = fontSize.sp,
        maxLines = lineLimit ?: Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun StoryCell_Preview(
    @PreviewParameter(PreviewThemeProvider::class) useDarkTheme: Boolean
) {
    val theme = if (useDarkTheme) DarkTheme() else LightTheme()
    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        StoryCell(HNApiMock.stories[0])
    }
}