package com.radreichley.noctal.android.base

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ArbitraryIndexProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int> = (0..100).asSequence()
}
