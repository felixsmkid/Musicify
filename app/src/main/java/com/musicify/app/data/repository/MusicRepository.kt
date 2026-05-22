package com.musicify.app.data.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.musicify.app.data.api.LrcLibApi
import com.musicify.app.data.api.PipedApi
import com.musicify.app.data.api.innertube.InnerTubeApi
import com.musicify.app.data.api.innertube.InnerTubeClient
import com.musicify.app.data.api.innertube.InnerTubeParser
import com.musicify.app.data.model.*
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val innerTubeApi: InnerTubeApi,
    private val pipedApi: PipedApi,
    private val lrcLibApi: LrcLibApi,
    private val okHttpClient: OkHttpClient
) {
    private val trendingQueries = listOf(
        "top songs this week",
        "popular music 2024",
        "trending hits",
        "new music friday"
    )

    suspend fun searchTrending(): Result<List<TrendingItem>> = runCatching {
        val query = trendingQueries.random()
        val response = innerTubeApi.search(InnerTubeClient.searchRequest(query))
        val items = InnerTubeParser.parseSearchAsTrending(response)
        if (items.isEmpty()) {
            val fallback = innerTubeApi.search(InnerTubeClient.searchRequest("best songs 2024"))
            InnerTubeParser.parseSearchAsTrending(fallback)
        } else items
    }

    suspend fun search(query: String): Result<SearchResult> = runCatching {
        val response = innerTubeApi.search(InnerTubeClient.searchRequest(query))
        val items = InnerTubeParser.parseSearch(response)
        SearchResult(items = items)
    }

    suspend fun getStreamUrl(videoId: String): Result<String> = runCatching {
        val response = innerTubeApi.player(InnerTubeClient.playerRequest(videoId))
        InnerTubeParser.parseStreamUrl(response)
            ?: throw Exception("No audio stream available")
    }

    suspend fun getStreamFromPiped(instanceUrl: String, videoId: String): Result<String> = runCatching {
        withContext(Dispatchers.IO) {
            val url = "$instanceUrl/streams/$videoId"
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "Musicify/0.3.0")
                .build()

            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) throw Exception("HTTP ${response.code}")

            val body = response.body?.string() ?: throw Exception("Empty body")
            val json = Gson().fromJson(body, JsonObject::class.java)

            val audioStreams = json.getAsJsonArray("audioStreams")
                ?: throw Exception("No audioStreams")

            val best = audioStreams
                .map { it.asJsonObject }
                .filter { it.has("url") && it.get("url").asString.isNotEmpty() }
                .maxByOrNull { it.get("bitrate")?.asInt ?: 0 }
                ?: throw Exception("No valid audio stream")

            best.get("url").asString
        }
    }

    suspend fun getStream(videoId: String): Result<StreamData> = runCatching {
        pipedApi.getStream(videoId)
    }

    suspend fun getLyrics(trackName: String, artistName: String, duration: Int = 0): Result<LyricsResult> = runCatching {
        lrcLibApi.getLyrics(trackName, artistName, duration = duration)
    }

    suspend fun searchLyrics(query: String): Result<List<LyricsResult>> = runCatching {
        lrcLibApi.searchLyrics(query = query)
    }

    fun parseSyncedLyrics(syncedLyrics: String): List<SyncedLyricLine> {
        val regex = Regex("""\[(\d{2}):(\d{2})\.(\d{2,3})\]\s*(.*)""")
        return syncedLyrics.lines().mapNotNull { line ->
            regex.find(line)?.let { match ->
                val minutes = match.groupValues[1].toLong()
                val seconds = match.groupValues[2].toLong()
                val millis = match.groupValues[3].let {
                    if (it.length == 2) it.toLong() * 10 else it.toLong()
                }
                val timeMs = minutes * 60000 + seconds * 1000 + millis
                val text = match.groupValues[4]
                SyncedLyricLine(timeMs, text)
            }
        }.sortedBy { it.timeMs }
    }
}
