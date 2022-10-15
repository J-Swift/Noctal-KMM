package com.radreichley.noctal.android.base

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.base.theming.ITheme
import com.radreichley.noctal.base.theming.Color as NoctalColor

fun NoctalColor.toPlatform(): Color {
    return Color(this.argbLongValue)
}

val LocalNoctalTheme = staticCompositionLocalOf<ITheme> { LightTheme() }