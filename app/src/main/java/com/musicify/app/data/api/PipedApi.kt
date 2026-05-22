package com.musicify.app.data.api

import com.musicify.app.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PipedApi {
    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("filter") filter: String = "music_songs"
    ): SearchResult

    @GET("streams/{videoId}")
    suspend fun getStream(@Path("videoId") videoId: String): StreamData

    @GET("trending")
    suspend fun getTrending(@Query("region") region: String = "US"): List<TrendingItem>

    @GET("playlists/{playlistId}")
    suspend fun getPlaylist(@Path("playlistId") playlistId: String): PlaylistData

    @GET("channels/{channelId}")
    suspend fun getChannel(@Path("channelId") channelId: String): ChannelData

    @GET("nextpage/search")
    suspend fun searchNextPage(
        @Query("q") query: String,
        @Query("filter") filter: String = "music_songs",
        @Query("nextpage") nextPage: String
    ): SearchResult
}
