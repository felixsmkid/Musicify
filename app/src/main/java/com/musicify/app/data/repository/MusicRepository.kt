package com.musicify.app.data.repository

import com.musicify.app.data.api.LrcLibApi
import com.musicify.app.data.api.PipedApi
import com.musicify.app.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val pipedApi: PipedApi,
    private val lrcLibApi: LrcLibApi
) {
    suspend fun search(query: String): Result<SearchResult> = runCatching {
        pipedApi.search(query)
    }

    suspend fun getStream(videoId: String): Result<StreamData> = runCatching {
        pipedApi.getStream(videoId)
    }

    suspend fun getTrending(): Result<List<TrendingItem>> = runCatching {
        pipedApi.getTrending()
    }

    suspend fun getLyrics(trackName: String, artistName: String, duration: Int = 0): Result<LyricsResult> = runCatching {
        lrcLibApi.getLyrics(trackName, artistName, duration = duration)
    }

    suspend fun searchLyrics(query: String): Result<List<LyricsResult>> = runCatching {
        lrcLibApi.searchLyrics(query = query)
    }

    suspend fun getPlaylist(playlistId: String): Result<PlaylistData> = runCatching {
        pipedApi.getPlaylist(playlistId)
    }

    fun getAudioStreamUrl(streamData: StreamData): String? {
        return streamData.audioStreams
            .filter { it.mimeType.contains("audio") }
            .maxByOrNull { it.bitrate }
            ?.url
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
