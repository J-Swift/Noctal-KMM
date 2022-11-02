package com.radreichley.noctal.android

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.module.meta_fetcher.meta_fetcher.MetaFetcher
import com.radreichley.noctal.module.meta_fetcher.meta_fetcher.RegexEngine


class AndroidEngine : RegexEngine
{
    override fun isMatch(pattern: String, target: String): Boolean {
        return pattern.toRegex(RegexOption.IGNORE_CASE).matches(target)
    }

    override fun matchesFor(pattern: String, target: String): List<String> {
        return pattern.toRegex(RegexOption.IGNORE_CASE).findAll(target).map {
            it.value
        }.toList()
    }

    override fun valueFor(pattern: String, target: String, groupNumber: Int?): String? {
        val res = pattern.toRegex(RegexOption.IGNORE_CASE).find(target)
        if (groupNumber == null) {
            return res?.value
        }

        return res?.let {
            it.groups[groupNumber]!!.value
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MetaFetcher.engine = AndroidEngine()
        setContent {
            AppWrapper(isSystemInDarkTheme())
        }
    }
}

@Composable
fun AppWrapper(useDarkTheme: Boolean) {
    val theme = if (useDarkTheme) DarkTheme() else LightTheme()

    return CompositionLocalProvider(LocalNoctalTheme provides theme) {
        MyApplicationTheme(useDarkTheme) {
            AppLayout()
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

@Preview(showSystemUi = true, group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppWrapper_Preview() {
    AppWrapper(isSystemInDarkTheme())
}
