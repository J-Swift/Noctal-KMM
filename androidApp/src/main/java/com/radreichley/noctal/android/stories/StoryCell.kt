@file:OptIn(ExperimentalGlideComposeApi::class)

package com.radreichley.noctal.android.stories

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.radreichley.noctal.android.base.ArbitraryIndexProvider
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.toPlatform
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.stories.*

private val dims = StoryCellConfig.Dims
private val styles = StoryCellConfig.Styling

@Composable
fun StoryCell(data: StoryWithMeta, index: Int, isSelected: Boolean = false) {
    val bgColor = when {
        (isSelected && isSystemInDarkTheme()) -> styles.CellHighlightDk
        (isSelected && !isSystemInDarkTheme()) -> styles.CellHighlightLt
        else -> LocalNoctalTheme.current.backgroundColor
    }
    val placeholderColor = styles.PlaceholderColors[index % styles.PlaceholderColors.count()]

    Row(
        modifier = Modifier
            .background(bgColor.toPlatform())
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
                .background(
                    placeholderColor.toPlatform(),
                    shape = RoundedCornerShape(dims.DimImgRadius.dp)
                )
                .clip(RoundedCornerShape(dims.DimImgRadius.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                data.story.placeholderLetter ?: "Y",
                color = Color.White,
                fontSize = styles.FontSizePlaceholder.sp
            )

            data.meta?.imagePath?.let {
                GlideImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = Modifier.weight(1.0f),
            verticalArrangement = Arrangement.spacedBy(dims.DimVPadding.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dims.DimHPaddingRow.dp)
            ) {
                StoryLabel("$index.")
                Box(
                    modifier = Modifier
                        .size(dims.DimImgFavicon.dp, dims.DimImgFavicon.dp)
                        .clip(RoundedCornerShape(50))
                ) {
                    data.meta?.favIconPath?.let {
                        GlideImage(model = it, contentDescription = null)
                    }
                }
                // TODO(jpr): autosizing text
                StoryLabel(
                    data.story.displayUrl ?: " ",
                    textColor = LocalNoctalTheme.current.primaryColor.toPlatform()
                )
            }

            StoryLabel(data.story.title, fontSize = styles.FontSizeTitle, lineLimit = null)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dims.DimHPaddingRow.dp)
            ) {
                StoryLabel(data.story.submitter)
                StoryLabel("???")
                StoryLabel("4h ago")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dims.DimHPaddingRow.dp)
            ) {
                StoryLabel("???")
                StoryLabel(data.story.score.toString())
                StoryLabel("???")
                StoryLabel("${data.story.numComments} comments")
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

@Preview(group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StoryCell_Preview(
    @PreviewParameter(ArbitraryIndexProvider::class, limit = 2) idx: Int
) {
    val theme = if (isSystemInDarkTheme()) DarkTheme() else LightTheme()
    val isSelected = listOf(false, true)[idx]

    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        StoryCell(
            StoryWithMeta(previewStories[0], previewStoryMetas[0]),
            index = 1,
            isSelected = isSelected
        )
    }
}