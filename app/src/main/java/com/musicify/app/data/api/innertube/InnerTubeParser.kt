package com.musicify.app.data.api.innertube

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.musicify.app.data.model.SearchItem
import com.musicify.app.data.model.TrendingItem

object InnerTubeParser {

    fun parseCharts(response: JsonObject): List<TrendingItem> {
        return parseSearchAsTrending(response)
    }

    fun parseSearchAsTrending(response: JsonObject): List<TrendingItem> {
        val items = mutableListOf<TrendingItem>()
        try {
            val contents = response
                .getAsJsonObject("contents")
                ?.getAsJsonObject("tabbedSearchResultsRenderer")
                ?.getAsJsonArray("tabs")
                ?.get(0)?.asJsonObject
                ?.getAsJsonObject("tabRenderer")
                ?.getAsJsonObject("content")
                ?.getAsJsonObject("sectionListRenderer")
                ?.getAsJsonArray("contents")

            contents?.forEach { section ->
                val shelf = section.asJsonObject?.getAsJsonObject("musicShelfRenderer")
                shelf?.getAsJsonArray("contents")?.forEach { item ->
                    parseTrendingFromSearch(item)?.let { items.add(it) }
                }
            }
        } catch (_: Exception) {}
        return items
    }

    private fun parseTrendingFromSearch(item: JsonElement): TrendingItem? {
        return try {
            val renderer = item.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemRenderer") ?: return null

            val cols = renderer.getAsJsonArray("flexColumns")
            val title = cols?.get(0)?.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemFlexColumnRenderer")
                ?.getAsJsonObject("text")
                ?.getAsJsonArray("runs")
                ?.get(0)?.asJsonObject
                ?.get("text")?.asString ?: return null

            val artistRuns = cols?.get(1)?.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemFlexColumnRenderer")
                ?.getAsJsonObject("text")
                ?.getAsJsonArray("runs")
            val artist = artistRuns?.joinToString("") { it.asJsonObject.get("text")?.asString ?: "" } ?: ""

            val videoId = renderer.getAsJsonObject("playlistItemData")
                ?.get("videoId")?.asString
                ?: extractVideoIdFromOverlay(renderer)
                ?: return null

            val thumbnail = renderer.getAsJsonObject("thumbnail")
                ?.getAsJsonObject("musicThumbnailRenderer")
                ?.getAsJsonObject("thumbnail")
                ?.getAsJsonArray("thumbnails")
                ?.lastOrNull()?.asJsonObject
                ?.get("url")?.asString ?: ""

            TrendingItem(
                url = "/watch?v=$videoId",
                title = title,
                thumbnail = thumbnail,
                uploaderName = artist,
                uploaderUrl = "",
                uploaderAvatar = "",
                duration = 0,
                views = 0,
                type = "stream"
            )
        } catch (_: Exception) {
            null
        }
    }

    fun parseSearch(response: JsonObject): List<SearchItem> {
        val items = mutableListOf<SearchItem>()
        try {
            val contents = response
                .getAsJsonObject("contents")
                ?.getAsJsonObject("tabbedSearchResultsRenderer")
                ?.getAsJsonArray("tabs")
                ?.get(0)?.asJsonObject
                ?.getAsJsonObject("tabRenderer")
                ?.getAsJsonObject("content")
                ?.getAsJsonObject("sectionListRenderer")
                ?.getAsJsonArray("contents")

            contents?.forEach { section ->
                val shelf = section.asJsonObject?.getAsJsonObject("musicShelfRenderer")
                shelf?.getAsJsonArray("contents")?.forEach { item ->
                    parseSearchItem(item)?.let { items.add(it) }
                }
            }
        } catch (_: Exception) {}
        return items
    }

    private fun parseSearchItem(item: JsonElement): SearchItem? {
        return try {
            val renderer = item.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemRenderer") ?: return null

            val cols = renderer.getAsJsonArray("flexColumns")
            val title = cols?.get(0)?.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemFlexColumnRenderer")
                ?.getAsJsonObject("text")
                ?.getAsJsonArray("runs")
                ?.get(0)?.asJsonObject
                ?.get("text")?.asString ?: return null

            val artistRuns = cols?.get(1)?.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemFlexColumnRenderer")
                ?.getAsJsonObject("text")
                ?.getAsJsonArray("runs")
            val artist = artistRuns?.joinToString("") { it.asJsonObject.get("text")?.asString ?: "" } ?: ""

            val videoId = renderer.getAsJsonObject("playlistItemData")
                ?.get("videoId")?.asString
                ?: extractVideoIdFromOverlay(renderer)
                ?: return null

            val thumbnail = renderer.getAsJsonObject("thumbnail")
                ?.getAsJsonObject("musicThumbnailRenderer")
                ?.getAsJsonObject("thumbnail")
                ?.getAsJsonArray("thumbnails")
                ?.lastOrNull()?.asJsonObject
                ?.get("url")?.asString ?: ""

            SearchItem(
                url = "/watch?v=$videoId",
                title = title,
                thumbnail = thumbnail,
                uploaderName = artist,
                uploaderUrl = "",
                duration = 0,
                views = 0,
                type = "stream"
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun extractVideoIdFromOverlay(renderer: JsonObject): String? {
        return try {
            renderer.getAsJsonObject("overlay")
                ?.getAsJsonObject("musicItemThumbnailOverlayRenderer")
                ?.getAsJsonObject("content")
                ?.getAsJsonObject("musicPlayButtonRenderer")
                ?.getAsJsonObject("playNavigationEndpoint")
                ?.getAsJsonObject("watchEndpoint")
                ?.get("videoId")?.asString
        } catch (_: Exception) {
            null
        }
    }

    fun parseStreamUrl(response: JsonObject): String? {
        return try {
            val formats = response.getAsJsonObject("streamingData")
                ?.getAsJsonArray("adaptiveFormats")

            formats?.mapNotNull { it.asJsonObject }
                ?.filter { it.get("mimeType")?.asString?.startsWith("audio/") == true }
                ?.maxByOrNull { it.get("bitrate")?.asInt ?: 0 }
                ?.get("url")?.asString
        } catch (_: Exception) {
            null
        }
    }
}
