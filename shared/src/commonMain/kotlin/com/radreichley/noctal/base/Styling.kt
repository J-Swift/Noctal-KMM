package com.radreichley.noctal.base

import com.radreichley.noctal.base.theming.Color
import com.radreichley.noctal.base.theming.ITheme

class LightTheme : ITheme {
    override val backgroundColor = Color("#FFFFFF")
    override val onBackgroundColor = Color("#000000")
    override val surfaceColor = Color("#E6E6E6")
    override val onSurfaceColor = Color("#000000")
    override val errorColor = Color("#FF0000")
    override val onErrorColor = Color("#FFFFFF")

    override val primaryColor = Color(29u, 124u, 243u)
    override val onPrimaryColor = Color("#FFFFFF")
    override val secondaryColor = Color(250u, 202u, 33u)
    override val onSecondaryColor = Color("#FFFFFF")
}

class DarkTheme : ITheme {
    override val backgroundColor = Color(42u, 43u, 45u)
    override val onBackgroundColor = Color("#FFFFFF")
    override val surfaceColor = Color(67u, 68u, 71u)
    override val onSurfaceColor = Color("#FFFFFF")
    override val errorColor = Color("#FF0000")
    override val onErrorColor = Color("#FFFFFF")

    override val primaryColor = Color(60u, 165u, 255u)
    override val onPrimaryColor = Color("#FFFFFF")
    override val secondaryColor = Color(230u, 145u, 104u)
    override val onSecondaryColor = Color("#FFFFFF")
}
