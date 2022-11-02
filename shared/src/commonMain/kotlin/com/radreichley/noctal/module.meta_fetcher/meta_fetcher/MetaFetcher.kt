package com.radreichley.noctal.module.meta_fetcher.meta_fetcher

import com.radreichley.noctal.module.meta_fetcher.IMetaFetcher
import com.radreichley.noctal.module.meta_fetcher.MetaResult
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds

interface RegexEngine {
    fun isMatch(pattern: String, target: String): Boolean
    fun matchesFor(pattern: String, target: String): List<String>
    fun valueFor(pattern: String, target: String, groupNumber: Int?): String?
}

class MetaFetcher : IMetaFetcher {
    companion object {
        const val bytesToRead = 1024
        const val maxBytesToRead = 1024 * 1024 // 1 MB
        val timeout = 10.seconds

        val favIconScoreMappings = hashMapOf(
            "apple-touch-icon" to 200,
            "shortcut icon" to 110,
            "icon" to 100,
        )

        var engine: RegexEngine? = null
    }

    override suspend fun getMetaAsync(urlPath: String): MetaResult {
        var result = MetaResult(null, null)
        val engine = engine ?: throw Exception("Must set a RegexEngine")

        try {
            val url = try {
                Url(urlPath)
            } catch (e: URLParserException) {
                null
            }

            if (url == null) {
                println("Invalid uri: [$urlPath]")
                return result
            }

            HttpClient().use { httpClient ->
                withTimeout(timeout) {
//                    httpClient.prepareGet(urlPath).execute { httpResponse ->
                    val body = httpClient.get(urlPath).bodyAsText()
                    val parser = MetaParser()
                    parser.parse(engine, body)


//                        val channel = httpResponse.bodyAsChannel()
//
//                        val parser = MetaParser()
//                        var keepGoing = !channel.isClosedForRead
//
//                        var totalBytesRemaining = maxBytesToRead
//                        while (keepGoing) {
//                            val packet = channel.readRemaining(bytesToRead.toLong())
//
//                            keepGoing = !channel.isClosedForRead
//
//                            var bytesRead = 0
//                            while (keepGoing && !packet.isEmpty) {
//                                val bytes = packet.readBytes()
//                                bytesRead += bytes.size
//                                totalBytesRemaining -= bytes.size
//                                keepGoing = keepGoing && totalBytesRemaining > 0 && parser.parse(
//                                    engine,
//                                    String(bytes, charset = Charsets.UTF_8)
//                                )
//                            }
//                            packet.close()
//
//                            keepGoing = keepGoing && bytesRead > 0
//                        }
//
//                        httpResponse.discardRemaining()

                    var favIconScore = -1

                    val allResults = parser.getResults(engine)
                    var idx = 0
                    for (parserResult in allResults) {
                        idx += 1
//                        println("JIMMY checking [$idx] of [${allResults.size}]")
                        when (parserResult) {
                            is MetaParser.ImageResult.OgImageResult -> {
//                                println("JIMMY og [${parserResult.ogProperty}]")
                                if (parserResult.ogProperty.equals("og:image", true)) {
//                                    println("JIMMY urlPath [${parserResult.urlPath}]")
                                    val res = try {
                                        val tempResult = Url(parserResult.urlPath)

                                        if (tempResult.host != "localhost")
                                            tempResult
                                        else
                                            URLBuilder(url).let {
                                                it.path(parserResult.urlPath)
                                                it.build()
                                            }
                                    } catch (e: URLParserException) {
                                        null
                                    }
//                                    println("JIMMY adding 1")
                                    result = result.copy(ogImagePath = res?.toString())
                                }
                            }
                            is MetaParser.ImageResult.FaviconResult -> {
//                                println("JIMMY favicon [${parserResult.iconType}]")

                                val score =
                                    favIconScoreMappings[parserResult.iconType.lowercase()]
                                if (score != null && score > favIconScore) {
                                    favIconScore = score
                                    val res = try {
                                        val tempResult = Url(parserResult.urlPath)

                                        if (tempResult.host != "localhost")
                                            tempResult
                                        else
                                            URLBuilder(url).let {
                                                it.path(parserResult.urlPath)
                                                it.build()
                                            }
                                    } catch (e: URLParserException) {
                                        null
                                    }
//                                    println("JIMMY adding 2")
                                    result = result.copy(favIconPath = res?.toString())
                                }
                            }
                        }
                    }
//                    println("JIMMY done checking 1")
//                    }
//                    println("JIMMY done checking 2")
                }
//                println("JIMMY done checking 3")
            }
//            println("JIMMY done checking 4")
        } catch (e: Exception) {
            println("Error fetching meta [$urlPath]")
            println(e.message)
        }
//        println("JIMMY 8 [$result]")

        return result
    }
}

internal class MetaParser {
    companion object {
        val headRegex = Regex("<head>", RegexOption.IGNORE_CASE)
        val headCloseRegex = Regex("</head>", RegexOption.IGNORE_CASE)

        // TODO(jpr): single quotes

        val metaRegex = Regex("<meta [^>]+>", RegexOption.IGNORE_CASE)
        val metaPropertyRegex =
            Regex("\\bproperty=\"(?<value>[^\"]+)\"", RegexOption.IGNORE_CASE)
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

    fun parse(engine: RegexEngine, nextHtmlChunk: String): Boolean {
        _buffer += nextHtmlChunk

        if (!_inHead && engine.isMatch(headRegex.pattern, _buffer)) {
            _inHead = true
        }

        if (_inHead && engine.isMatch(headCloseRegex.pattern, _buffer)) {
            _keepGoing = false
        }

        return _keepGoing
    }

    fun getResults(engine: RegexEngine): List<ImageResult> {
        val result = mutableListOf<ImageResult>()
        try {
            for (link in engine.matchesFor(linkRegex.pattern, _buffer)) {
                val rel = engine.valueFor(linkRelRegex.pattern, link, 1)
                val urlPath = engine.valueFor(linkHrefRegex.pattern, link, 1)

                if (rel != null && urlPath != null) {
//                    println("JIMMY getResults 3.2 writing")
                    result.add(
                        ImageResult.FaviconResult(urlPath, rel)
                    )
                }
            }

            for (meta in engine.matchesFor(metaRegex.pattern, _buffer)) {
                val ogType = engine.valueFor(metaPropertyRegex.pattern, meta, 1)
                val urlPath = engine.valueFor(metaContentRegex.pattern, meta, 1)

                if (ogType != null && urlPath != null) {
//                    println("JIMMY getResults 3.1 writing")
                    result.add(
                        ImageResult.OgImageResult(urlPath, ogType)
                    )
                }
            }
        } catch (e: Exception) {
            println("JIMMY getResults e")
            println(e.message)
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
