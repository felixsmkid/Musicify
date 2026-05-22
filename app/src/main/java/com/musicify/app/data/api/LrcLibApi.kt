package com.musicify.app.data.api

import com.musicify.app.data.model.LyricsResult
import retrofit2.http.GET
import retrofit2.http.Query

interface LrcLibApi {
    @GET("api/get")
    suspend fun getLyrics(
        @Query("track_name") trackName: String,
        @Query("artist_name") artistName: String,
        @Query("album_name") albumName: String = "",
        @Query("duration") duration: Int = 0
    ): LyricsResult

    @GET("api/search")
    suspend fun searchLyrics(
        @Query("track_name") trackName: String = "",
        @Query("artist_name") artistName: String = "",
        @Query("q") query: String = ""
    ): List<LyricsResult>
}
