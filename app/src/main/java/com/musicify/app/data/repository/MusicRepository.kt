package com.musicify.app.data.repository

import com.musicify.app.data.api.LrcLibApi
import com.musicify.app.data.api.PipedApi
import com.musicify.app.data.api.innertube.InnerTubeApi
import com.musicify.app.data.api.innertube.InnerTubeClient
import com.musicify.app.data.api.innertube.InnerTubeParser
import com.musicify.app.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val innerTubeApi: InnerTubeApi,
    private val pipedApi: PipedApi,
    private val lrcLibApi: LrcLibApi
) {
    suspend fun getTrending(): Result<List<TrendingItem>> = runCatching {
        val response = innerTubeApi.browse(InnerTubeClient.browseChartsRequest())
        InnerTubeParser.parseCharts(response)
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
