package com.radreichley.noctal.android.base

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class PreviewThemeProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(false, true)
}