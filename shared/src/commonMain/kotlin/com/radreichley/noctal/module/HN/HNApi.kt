package com.radreichley.noctal.module.HN.models

import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromStringOrNull
import kotlinx.datetime.Instant

enum class StoryType {
    Story,
    ShowHn,
    AskHn,
}

data class Story(
    val id: String,
    val title: String,
    val author: String,
    val urlPath: String?,
    val createdAt: Instant,
    val score: Int,
    val numComments: Int,
    val typeOfStore: StoryType,
    val storyText: String?
) {
    private val urlHost: String? by lazy {
        val dVal = null

        urlPath ?: return@lazy dVal
        val uri = Uri.fromStringOrNull(urlPath) ?: return@lazy dVal

        uri.host ?: dVal
    }

    val displayUrl: String? by lazy {
        val dVal = urlPath

        var host = urlHost ?: return@lazy dVal

        if (host.startsWith("www.")) {
            host = host.substring(4)
        }

        host
    }

    val placeholderLetter: String? by lazy {
        val dVal = null

        val host = urlHost ?: return@lazy dVal
        val parts = host.split(".")
        parts[parts.count() - 2][0].toString().uppercase()
    }
}
