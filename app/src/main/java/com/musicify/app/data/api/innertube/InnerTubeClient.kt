package com.musicify.app.data.api.innertube

import com.google.gson.JsonObject

object InnerTubeClient {
    private const val CLIENT_NAME = "WEB_REMIX"
    private const val CLIENT_VERSION = "1.20241120.01.00"

    fun createContext(hl: String = "en", gl: String = "US"): JsonObject {
        return JsonObject().apply {
            add("context", JsonObject().apply {
                add("client", JsonObject().apply {
                    addProperty("clientName", CLIENT_NAME)
                    addProperty("clientVersion", CLIENT_VERSION)
                    addProperty("hl", hl)
                    addProperty("gl", gl)
                })
            })
        }
    }

    fun browseChartsRequest(gl: String = "US"): JsonObject {
        return createContext(gl = gl).apply {
            addProperty("browseId", "FEmusic_charts")
        }
    }

    fun browseHomeRequest(): JsonObject {
        return createContext().apply {
            addProperty("browseId", "FEmusic_home")
        }
    }

    fun searchRequest(query: String): JsonObject {
        return createContext().apply {
            addProperty("query", query)
            add("params", null)
        }
    }

    fun playerRequest(videoId: String): JsonObject {
        return JsonObject().apply {
            add("context", JsonObject().apply {
                add("client", JsonObject().apply {
                    addProperty("clientName", "ANDROID_MUSIC")
                    addProperty("clientVersion", "7.27.52")
                    addProperty("androidSdkVersion", 30)
                    addProperty("hl", "en")
                    addProperty("gl", "US")
                })
            })
            addProperty("videoId", videoId)
        }
    }

    fun nextRequest(videoId: String): JsonObject {
        return createContext().apply {
            addProperty("videoId", videoId)
            addProperty("isAudioOnly", true)
        }
    }
}
