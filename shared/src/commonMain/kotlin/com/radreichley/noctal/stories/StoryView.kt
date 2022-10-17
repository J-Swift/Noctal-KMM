package com.radreichley.noctal.stories

import com.radreichley.noctal.base.theming.Color

object StoryCellConfig {
    object Dims {
        const val DimImg = 70.0
        const val DimImgFavicon = 16.0
        const val DimImgRadius = 4.0
        const val DimVPadding = 16.0
        const val DimHPaddingRow = 4.0
        const val DimHPadding = 20.0

        const val DimEstimatedCellHeight = 160.0
    }

    object Styling {
        const val FontSizeDefault = 19.0
        const val FontSizeTitle = 24.0
        const val FontSizePlaceholder = 32.0

        val CellHighlightLt = Color(180u, 215u, 250u)
        val CellHighlightDk = Color(28u, 104u, 185u)

        val PlaceholderColors = listOf(
            Color(149u, 176u, 214u),
            Color(143u, 144u, 161u),
            Color(81u, 91u, 92u),
            Color(52u, 63u, 62u),
        )
    }
}
