package com.radreichley.noctal.module.HN.models

import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromStringOrNull
import kotlinx.datetime.Instant

enum class StoryType {
    Story,
    ShowHn,
    AskHn,
}

data class StoryDto(
    val id: String,
    val title: String,
    val author: String,
    val urlPath: String?,
    val createdAt: Instant,
    val score: Int,
    val numComments: Int,
    val typeOfStore: StoryType,
    val storyText: String?

)
