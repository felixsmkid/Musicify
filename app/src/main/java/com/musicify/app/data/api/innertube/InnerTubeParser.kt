package com.musicify.app.data.api.innertube

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.musicify.app.data.model.SearchItem
import com.musicify.app.data.model.TrendingItem

object InnerTubeParser {

    fun parseSearchAsTrending(response: JsonObject): List<TrendingItem> {
        return parseSearchItems(response).map { item ->
            TrendingItem(
                url = item.url,
                title = item.title,
                thumbnail = item.thumbnail,
                uploaderName = item.uploaderName,
                uploaderUrl = "",
                uploaderAvatar = "",
                duration = item.duration,
                views = item.views,
                type = "stream"
            )
        }
    }

    fun parseSearch(response: JsonObject): List<SearchItem> {
        return parseSearchItems(response)
    }

    private fun parseSearchItems(response: JsonObject): List<SearchItem> {
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
                    parseItem(item)?.let { items.add(it) }
                }
            }
        } catch (_: Exception) {}
        return items
    }

    private fun parseItem(item: JsonElement): SearchItem? {
        return try {
            val renderer = item.asJsonObject
                ?.getAsJsonObject("musicResponsiveListItemRenderer") ?: return null

            val cols = renderer.getAsJsonArray("flexColumns") ?: return null

            // Title from flexCol 0
            val title = getFlexColumnText(cols, 0) ?: return null

            // Artist + duration from flexCol 1
            val col1Runs = getFlexColumnRuns(cols, 1)
            val artist = col1Runs?.firstOrNull()?.asJsonObject?.get("text")?.asString ?: ""
            val durationStr = col1Runs?.lastOrNull()?.asJsonObject?.get("text")?.asString ?: ""
            val duration = parseDuration(durationStr)

            // VideoId from playlistItemData
            val videoId = renderer.getAsJsonObject("playlistItemData")
                ?.get("videoId")?.asString ?: return null

            // Thumbnail
            val thumbnail = renderer.getAsJsonObject("thumbnail")
                ?.getAsJsonObject("musicThumbnailRenderer")
                ?.getAsJsonObject("thumbnail")
                ?.getAsJsonArray("thumbnails")
                ?.let { thumbs ->
                    if (thumbs.size() > 0) thumbs.last().asJsonObject.get("url")?.asString else null
                } ?: ""

            // Play count from flexCol 2
            val playsStr = getFlexColumnText(cols, 2) ?: ""
            val views = parseViews(playsStr)

            SearchItem(
                url = "/watch?v=$videoId",
                title = title,
                thumbnail = thumbnail,
                uploaderName = artist,
                uploaderUrl = "",
                duration = duration,
                views = views,
                type = "stream"
            )
        } catch (_: Exception) {
            null
        }
    }

    private fun getFlexColumnText(cols: JsonArray, index: Int): String? {
        if (index >= cols.size()) return null
        return try {
            cols.get(index).asJsonObject
                .getAsJsonObject("musicResponsiveListItemFlexColumnRenderer")
                .getAsJsonObject("text")
                .getAsJsonArray("runs")
                .get(0).asJsonObject
                .get("text")?.asString
        } catch (_: Exception) {
            null
        }
    }

    private fun getFlexColumnRuns(cols: JsonArray, index: Int): JsonArray? {
        if (index >= cols.size()) return null
        return try {
            cols.get(index).asJsonObject
                .getAsJsonObject("musicResponsiveListItemFlexColumnRenderer")
                .getAsJsonObject("text")
                .getAsJsonArray("runs")
        } catch (_: Exception) {
            null
        }
    }

    private fun parseDuration(str: String): Long {
        // Format: "4:59" or "1:04:59"
        val parts = str.split(":")
        return try {
            when (parts.size) {
                2 -> parts[0].toLong() * 60 + parts[1].toLong()
                3 -> parts[0].toLong() * 3600 + parts[1].toLong() * 60 + parts[2].toLong()
                else -> 0L
            }
        } catch (_: Exception) {
            0L
        }
    }

    private fun parseViews(str: String): Long {
        // Format: "960M plays" or "1.2B plays" or "500K plays"
        val cleaned = str.replace(" plays", "").replace(",", "").trim()
        return try {
            when {
                cleaned.endsWith("B") -> (cleaned.dropLast(1).toDouble() * 1_000_000_000).toLong()
                cleaned.endsWith("M") -> (cleaned.dropLast(1).toDouble() * 1_000_000).toLong()
                cleaned.endsWith("K") -> (cleaned.dropLast(1).toDouble() * 1_000).toLong()
                else -> cleaned.toLongOrNull() ?: 0L
            }
        } catch (_: Exception) {
            0L
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
