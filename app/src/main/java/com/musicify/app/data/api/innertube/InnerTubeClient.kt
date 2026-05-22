package com.musicify.app.data.api.innertube

import com.google.gson.JsonObject

object InnerTubeClient {
    private const val CLIENT_NAME = "WEB_REMIX"
    private const val CLIENT_VERSION = "1.20241120.01.00"
    private const val SONGS_FILTER = "EgWKAQIIAWoKEAkQBRAKEAMQBA%3D%3D"

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

    fun searchRequest(query: String, filterSongs: Boolean = true): JsonObject {
        return createContext().apply {
            addProperty("query", query)
            if (filterSongs) {
                addProperty("params", SONGS_FILTER)
            }
        }
    }

    fun playerRequest(videoId: String): JsonObject {
        return JsonObject().apply {
            add("context", JsonObject().apply {
                add("client", JsonObject().apply {
                    addProperty("clientName", "ANDROID_MUSIC")
                    addProperty("clientVersion", "7.27.52")
                    addProperty("androidSdkVersion", 34)
                    addProperty("osName", "Android")
                    addProperty("osVersion", "14")
                    addProperty("platform", "MOBILE")
                    addProperty("hl", "en")
                    addProperty("gl", "US")
                })
                add("user", JsonObject().apply {
                    addProperty("lockedSafetyMode", false)
                })
            })
            addProperty("videoId", videoId)
            addProperty("racyCheckOk", true)
            addProperty("contentCheckOk", true)
            add("playbackContext", JsonObject().apply {
                add("contentPlaybackContext", JsonObject().apply {
                    addProperty("signatureTimestamp", 20073)
                })
            })
        }
    }

    fun nextRequest(videoId: String): JsonObject {
        return createContext().apply {
            addProperty("videoId", videoId)
            addProperty("isAudioOnly", true)
        }
    }
}
