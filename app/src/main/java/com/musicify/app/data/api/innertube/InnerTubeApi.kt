package com.musicify.app.data.api.innertube

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface InnerTubeApi {
    @POST("browse")
    @Headers("Content-Type: application/json")
    suspend fun browse(
        @Body body: JsonObject,
        @Query("prettyPrint") prettyPrint: Boolean = false
    ): JsonObject

    @POST("search")
    @Headers("Content-Type: application/json")
    suspend fun search(
        @Body body: JsonObject,
        @Query("prettyPrint") prettyPrint: Boolean = false
    ): JsonObject

    @POST("player")
    @Headers("Content-Type: application/json")
    suspend fun player(
        @Body body: JsonObject,
        @Query("prettyPrint") prettyPrint: Boolean = false
    ): JsonObject

    @POST("next")
    @Headers("Content-Type: application/json")
    suspend fun next(
        @Body body: JsonObject,
        @Query("prettyPrint") prettyPrint: Boolean = false
    ): JsonObject
}
