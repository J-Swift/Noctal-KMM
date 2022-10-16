package com.radreichley.noctal.module.HN.HNApi

import com.radreichley.noctal.module.HN.models.Story

interface IHNApi {
    suspend fun getStoriesAsync(): List<Story>
}