package com.radreichley.noctal.android

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.radreichley.noctal.android.account.AccountView
import com.radreichley.noctal.android.base.LocalNoctalTheme
import com.radreichley.noctal.android.base.PreviewThemeProvider
import com.radreichley.noctal.android.search.SearchView
import com.radreichley.noctal.android.settings.SettingsView
import com.radreichley.noctal.android.stories.StoryView
import com.radreichley.noctal.base.DarkTheme
import com.radreichley.noctal.base.LightTheme
import com.radreichley.noctal.base.navigation.NavRoute
import com.radreichley.noctal.base.navigation.isRoute

@Composable
fun AppLayout(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    return Scaffold(bottomBar = {
        BottomNavigation {
            NavItem(
                route = NavRoute.Stories,
                currentRoute = currentRoute,
                onClick = { navController.navigate(NavRoute.Stories.path) })
            NavItem(
                route = NavRoute.Search,
                currentRoute = currentRoute,
                onClick = { navController.navigate(NavRoute.Search.path) })
            NavItem(
                route = NavRoute.Account,
                currentRoute = currentRoute,
                onClick = { navController.navigate(NavRoute.Account.path) })
            NavItem(
                route = NavRoute.Settings,
                currentRoute = currentRoute,
                onClick = { navController.navigate(NavRoute.Settings.path) })
        }
    }) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.Stories.path
        ) {
            composable(NavRoute.Stories.path) {
                StoryView(modifier = Modifier.padding(contentPadding))
            }
            composable(NavRoute.Search.path) {
                SearchView(modifier = Modifier.padding(contentPadding))
            }
            composable(NavRoute.Account.path) {
                AccountView(modifier = Modifier.padding(contentPadding))
            }
            composable(NavRoute.Settings.path) {
                SettingsView(modifier = Modifier.padding(contentPadding))
            }
        }
    }
}

@Composable
private fun RowScope.NavItem(route: NavRoute, currentRoute: String, onClick: () -> Unit) {
    return BottomNavigationItem(
        selected = currentRoute.isRoute(route),
        onClick = onClick,
        label = { Text(route.displayName) },
        icon = { Icon(Icons.Outlined.Add, null) }
    )
}

@Preview(showSystemUi = true)
@Composable
fun MainScene_Preview(
    @PreviewParameter(PreviewThemeProvider::class) useDarkTheme: Boolean
) {
    val theme = if (useDarkTheme) DarkTheme() else LightTheme()
    CompositionLocalProvider(LocalNoctalTheme provides theme) {
        AppLayout()
    }
}
