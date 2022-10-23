package com.radreichley.noctal.module.meta_fetcher.meta_fetcher

import com.radreichley.noctal.module.meta_fetcher.IMetaFetcher
import com.radreichley.noctal.module.meta_fetcher.MetaResult
import kotlinx.coroutines.delay

class MetaFetcherMock : IMetaFetcher {
    override suspend fun getMetaAsync(urlPath: String): MetaResult {
        delay(2000)

        return MetaResult(null, null)
    }
}