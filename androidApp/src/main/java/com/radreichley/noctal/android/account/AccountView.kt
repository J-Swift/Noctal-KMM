package com.radreichley.noctal.android.account

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.toPlatform
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme

@Composable
fun AccountView(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .fillMaxSize()
            .background(
                LocalNoctalTheme.current.backgroundColor.toPlatform()
            )
    ) {
        Text(text = "Account Page", color = LocalNoctalTheme.current.onBackgroundColor.toPlatform())
    }
}

@Preview(showSystemUi = true, group = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, group = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AccountView_Preview() {
    val theme = if (isSystemInDarkTheme()) DarkTheme() else LightTheme()

    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        AccountView()
    }
}