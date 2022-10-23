package com.radreichley.noctal.module.HN.HNApi

import com.radreichley.noctal.module.HN.models.StoryDto

interface IHNApi {
    suspend fun getStoriesAsync(): List<StoryDto>
}