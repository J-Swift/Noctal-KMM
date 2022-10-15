package com.radreichley.noctal.android.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.radreichley.noctal.stories.StoryCellConfig
import com.radreichley.noctal.base.theming.Color as NoctalColor

private val debugColor = NoctalColor.DEBUG_placeholder()
private val dims = StoryCellConfig.Dims
private val styles = StoryCellConfig.Styling

@Composable
fun StoryCell() {
    Row(modifier = Modifier
        .padding(vertical = dims.DimVPadding.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .padding(horizontal = dims.DimHPadding.dp)
                .size(dims.DimImg.dp, dims.DimImg.dp)
                .background(debugColor, shape = RoundedCornerShape(dims.DimImgRadius.dp))
        )

        Column(
            modifier = Modifier.weight(1.0f),
            verticalArrangement = Arrangement.spacedBy(dims.DimVPadding.dp)
        ) {
            StoryLabel("1.", styles.FontSizeTitle)
            StoryLabel("This is the titlez", styles.FontSizeTitle)
            StoryLabel("JamesSwift", styles.FontSizeTitle)
            StoryLabel("311", styles.FontSizeTitle)
        }
    }
}

@Composable
fun StoryLabel(text: String, fontSize: Double = styles.FontSizeDefault) {
    Text(
        text,
        color = LocalNoctalTheme.current.onBackgroundColor.toPlatform(),
        fontSize = fontSize.sp
    )
}

@Preview(showBackground = true)
@Composable
fun StoryCell_Preview(
    @PreviewParameter(PreviewThemeProvider::class) useDarkTheme: Boolean
) {
    val theme = if (useDarkTheme) DarkTheme() else LightTheme()
    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        StoryCell()
    }
}