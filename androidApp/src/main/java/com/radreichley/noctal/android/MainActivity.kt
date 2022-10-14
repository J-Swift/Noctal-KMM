package com.radreichley.noctal.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.base.theming.ITheme
import com.radreichley.noctal.base.theming.Color as NoctalColor

fun NoctalColor.toPlatform(): Color {
    return Color(this.longValue)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppWrapper()
        }
    }
}

val LocalNoctalTheme = staticCompositionLocalOf<ITheme> { error("Theme not set") }

@Composable
fun AppWrapper(useDarkTheme: Boolean = isSystemInDarkTheme()) {
    val theme = if (useDarkTheme) DarkTheme() else LightTheme()

    return CompositionLocalProvider(LocalNoctalTheme provides theme) {
        MyApplicationTheme(useDarkTheme) {
            Greeting("HI")
        }
    }
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@Composable
fun Greeting(text: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LocalNoctalTheme.current.surfaceColor.toPlatform(),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, color = LocalNoctalTheme.current.onSurfaceColor.toPlatform())
        }
    }
}

class PreviewThemeProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(false, true)
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview(
    @PreviewParameter(PreviewThemeProvider::class) useDarkTheme: Boolean
) {
    AppWrapper(useDarkTheme)
}
