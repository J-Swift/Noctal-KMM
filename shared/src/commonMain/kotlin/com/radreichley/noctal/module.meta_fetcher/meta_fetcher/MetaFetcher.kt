package com.radreichley.noctal.module.meta_fetcher.meta_fetcher

import com.chrynan.uri.core.Uri
import com.chrynan.uri.core.fromPartsOrNull
import com.chrynan.uri.core.fromStringOrNull
import com.radreichley.noctal.module.meta_fetcher.IMetaFetcher
import com.radreichley.noctal.module.meta_fetcher.MetaResult
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds

class MetaFetcher : IMetaFetcher {
    companion object {
        const val bytesToRead = 2000

        val favIconScoreMappings = hashMapOf(
            "apple-touch-icon" to 200,
            "shortcut icon" to 110,
            "icon" to 100,
        )
    }

    override suspend fun getMetaAsync(urlPath: String): MetaResult {
        var result = MetaResult(null, null)

        try {
            val url = Uri.fromStringOrNull(urlPath)
            if (url == null) {
                println("Invalid uri: [$urlPath]")
                return result
            }

            HttpClient().use { httpClient ->
                withTimeout(5.seconds) {
                    httpClient.prepareGet(urlPath).execute { httpResponse ->
                        val channel = httpResponse.bodyAsChannel()

                        val parser = MetaParser()
                        var keepGoing = !channel.isClosedForRead

                        while (keepGoing) {
                            val packet = channel.readRemaining(bytesToRead.toLong())

                            keepGoing = !channel.isClosedForRead

                            var bytesRead = 0
                            while (!packet.isEmpty) {
                                val bytes = packet.readBytes()
                                bytesRead += bytes.size
                                keepGoing = keepGoing && parser.parse(
                                    String(bytes, charset = Charsets.UTF_8)
                                )
                            }

                            keepGoing = keepGoing && bytesRead > 0
                        }

                        httpResponse.discardRemaining()

                        var favIconScore = -1

                        for (parserResult in parser.getResults()) {
                            when (parserResult) {
                                is MetaParser.ImageResult.OgImageResult -> {
                                    if (parserResult.ogProperty.equals("og:image", true)) {
                                        val res = Uri.fromStringOrNull(parserResult.urlPath)
                                            ?: Uri.fromPartsOrNull(
                                                scheme = url.scheme,
                                                host = url.host,
                                                path = parserResult.urlPath
                                            )
                                        result = result.copy(ogImagePath = res?.uriString)
                                    }
                                }
                                is MetaParser.ImageResult.FaviconResult -> {
                                    val score =
                                        favIconScoreMappings[parserResult.iconType.lowercase()]
                                    if (score != null && score > favIconScore) {
                                        favIconScore = score
                                        val res = Uri.fromStringOrNull(parserResult.urlPath)
                                            ?: Uri.fromPartsOrNull(
                                                scheme = url.scheme,
                                                host = url.host,
                                                path = parserResult.urlPath
                                            )
                                        result = result.copy(favIconPath = res?.uriString)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("Error fetching meta [$urlPath]")
            println(e.message)
        }

        return result
    }
}

internal class MetaParser {
    companion object {
        val headRegex = Regex("<head>", RegexOption.IGNORE_CASE)
        val headCloseRegex = Regex("</head>", RegexOption.IGNORE_CASE)

        // TODO(jpr): single quotes

        val metaRegex = Regex("<meta [^>]+>", RegexOption.IGNORE_CASE)
        val metaPropertyRegex = Regex("\\bproperty=\"(?<value>[^\"]+)\"", RegexOption.IGNORE_CASE)
        val metaContentRegex = Regex("\\bcontent=\"(?<value>[^\"]+)\"", RegexOption.IGNORE_CASE)

        val linkRegex = Regex("<link [^>]+>", RegexOption.IGNORE_CASE)
        val linkRelRegex = Regex("\\brel=\"(?<value>[^\"]+)\"", RegexOption.IGNORE_CASE)
        val linkHrefRegex = Regex("\\bhref=\"(?<value>[^\"]+)\"", RegexOption.IGNORE_CASE)
    }

    private var _buffer = ""
    private var _inHead = false

    // <meta property="og:image" content="https://assets.reedpopcdn.com/ace_attorney_2_u3krTvK.jpg/BROK/thumbnail/1600x900/format/jpg/quality/80/ace_attorney_2_u3krTvK.jpg">
    // <link rel="apple-touch-icon" href="/static/a9f2b837e17a27f9bcc4c9fe84dd94ec/icon/apple-touch-icon-152x152.png">
    // <link rel="shortcut icon" href="/static/a9f2b837e17a27f9bcc4c9fe84dd94ec/icon/favicon.ico">
    // <link rel="icon" type="image/png" href="/path/to/icons/favicon-192x192.png" sizes="192x192">
    // <link rel="apple-touch-icon" sizes="180x180" href="/path/to/icons/apple-touch-icon-180x180.png">
    // <link rel="shortcut icon" href="/path/to/icons/favicon.ico">

    private var _keepGoing = true

    fun parse(nextHtmlChunk: String): Boolean {
        _buffer += nextHtmlChunk

        if (!_inHead && headRegex.matches(_buffer)) {
            _inHead = true
        }

        if (_inHead && headCloseRegex.matches(_buffer)) {
            _keepGoing = false
        }

        return _keepGoing
    }

    fun getResults(): List<ImageResult> {
        val result = mutableListOf<ImageResult>()

        for (link in linkRegex.findAll(_buffer)) {
            val rel = linkRelRegex.find(link.value)
            val urlPath = linkHrefRegex.find(link.value)

            if (rel != null && urlPath != null) {
                result.add(
                    ImageResult.FaviconResult(
                        urlPath.groups[1]!!.value,
                        rel.groups[1]!!.value
                    )
                )
            }
        }

        for (meta in metaRegex.findAll(_buffer)) {
            val ogType = metaPropertyRegex.find(meta.value)
            val urlPath = metaContentRegex.find(meta.value)

            if (ogType != null && urlPath != null) {
                result.add(
                    ImageResult.OgImageResult(
                        urlPath.groups[1]!!.value,
                        ogType.groups[1]!!.value
                    )
                )
            }
        }

        return result
    }

    sealed class ImageResult(open val urlPath: String) {
        data class OgImageResult(override val urlPath: String, val ogProperty: String) :
            ImageResult(urlPath)

        data class FaviconResult(override val urlPath: String, val iconType: String) :
            ImageResult(urlPath)
    }
}