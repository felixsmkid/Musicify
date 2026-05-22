package com.musicify.app.data.api.innertube

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.musicify.app.data.model.SearchItem
import com.musicify.app.data.model.TrendingItem

object InnerTubeParser {

    fun parseCharts(response: JsonObject): List<TrendingItem> {
        val items = mutableListOf<TrendingItem>()
        try {
            val contents = response
                .getAsJsonObject("contents")
                ?.getAsJsonObject("singleColumnBrowseResultsRenderer")
                ?.getAsJsonArray("tabs")
                ?.get(0)?.asJsonObject
                ?.getAsJsonObject("tabRenderer")
                ?.getAsJsonObject("content")
                ?.getAsJsonObject("sectionListRenderer")
                ?.getAsJsonArray("contents")

            contents?.forEach { section ->
                val musicShelf = section.asJsonObject
                    ?.getAsJsonObject("musicCarouselShelfRenderer")
                    ?: section.asJsonObject?.getAsJsonObject("musicShelfRenderer")

                musicShelf?.getAsJsonArray("contents")?.forEach { item ->
                    parseChartItem(item)?.let { items.add(it) }
                }
            }
        } catch (_: Exception) {}
        return items
    }

    private fun parseChartItem(item: JsonElement): TrendingItem? {
        return try {
            val renderer = item.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemRenderer")
                ?: item.asJsonObject?.getAsJsonObject("musicTwoRowItemRenderer")

            if (renderer == null) return null

            val title = extractText(renderer, "flexColumns", 0)
                ?: extractText(renderer, "title")
                ?: return null

            val artist = extractText(renderer, "flexColumns", 1)
                ?: extractText(renderer, "subtitle")
                ?: ""

            val videoId = extractVideoId(renderer) ?: return null
            val thumbnail = extractThumbnail(renderer)

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
                val shelf = section.asJsonObject
                    ?.getAsJsonObject("musicShelfRenderer")

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
                ?.getAsJsonObject("musicResponsiveListItemRenderer")
                ?: return null

            val title = extractText(renderer, "flexColumns", 0) ?: return null
            val artist = extractText(renderer, "flexColumns", 1) ?: ""
            val videoId = extractVideoId(renderer) ?: return null
            val thumbnail = extractThumbnail(renderer)

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

    private fun extractText(renderer: JsonObject, key: String, index: Int = -1): String? {
        return try {
            if (key == "flexColumns" && index >= 0) {
                renderer.getAsJsonArray("flexColumns")
                    ?.get(index)?.asJsonObject
                    ?.getAsJsonObject("musicResponsiveListItemFlexColumnRenderer")
                    ?.getAsJsonObject("text")
                    ?.getAsJsonArray("runs")
                    ?.get(0)?.asJsonObject
                    ?.get("text")?.asString
            } else {
                renderer.getAsJsonObject(key)
                    ?.getAsJsonArray("runs")
                    ?.get(0)?.asJsonObject
                    ?.get("text")?.asString
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun extractVideoId(renderer: JsonObject): String? {
        return try {
            renderer.getAsJsonObject("overlay")
                ?.getAsJsonObject("musicItemThumbnailOverlayRenderer")
                ?.getAsJsonObject("content")
                ?.getAsJsonObject("musicPlayButtonRenderer")
                ?.getAsJsonObject("playNavigationEndpoint")
                ?.getAsJsonObject("watchEndpoint")
                ?.get("videoId")?.asString
                ?: renderer.getAsJsonObject("navigationEndpoint")
                    ?.getAsJsonObject("watchEndpoint")
                    ?.get("videoId")?.asString
                ?: renderer.getAsJsonObject("playlistItemData")
                    ?.get("videoId")?.asString
        } catch (_: Exception) {
            null
        }
    }

    private fun extractThumbnail(renderer: JsonObject): String {
        return try {
            val thumbnails = renderer.getAsJsonObject("thumbnail")
                ?.getAsJsonObject("musicThumbnailRenderer")
                ?.getAsJsonObject("thumbnail")
                ?.getAsJsonArray("thumbnails")
                ?: renderer.getAsJsonObject("thumbnailRenderer")
                    ?.getAsJsonObject("musicThumbnailRenderer")
                    ?.getAsJsonObject("thumbnail")
                    ?.getAsJsonArray("thumbnails")

            thumbnails?.lastOrNull()?.asJsonObject?.get("url")?.asString ?: ""
        } catch (_: Exception) {
            ""
        }
    }
}
