package com.radreichley.noctal.module.meta_fetcher

interface IMetaFetcher {
    suspend fun getMetaAsync(urlPath: String): MetaResult
}