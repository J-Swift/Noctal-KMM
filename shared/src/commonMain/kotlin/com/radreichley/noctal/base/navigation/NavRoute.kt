package com.radreichley.noctal.base.navigation

sealed class NavRoute(val path: String, val displayName: String) {
    object Stories : NavRoute("stories", "Stories")
    object Search : NavRoute("search", "Search")
    object Account : NavRoute("account", "Account")
    object Settings : NavRoute("settings", "Settings")
}

fun String.isRoute(route: NavRoute): Boolean {
    return this == route.path
}