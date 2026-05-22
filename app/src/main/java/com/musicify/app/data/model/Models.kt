package com.musicify.app.data.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    val items: List<SearchItem> = emptyList(),
    val nextpage: String? = null,
    val corrected: Boolean = false
)

data class SearchItem(
    val url: String = "",
    val title: String = "",
    val thumbnail: String = "",
    val uploaderName: String = "",
    val uploaderUrl: String = "",
    val duration: Long = 0,
    val views: Long = 0,
    val type: String = "stream"
)

data class StreamData(
    val title: String = "",
    val description: String = "",
    val uploadDate: String = "",
    val uploader: String = "",
    val uploaderUrl: String = "",
    val uploaderAvatar: String = "",
    val thumbnailUrl: String = "",
    val duration: Long = 0,
    val views: Long = 0,
    val likes: Long = 0,
    val audioStreams: List<AudioStream> = emptyList(),
    val videoStreams: List<VideoStream> = emptyList(),
    val relatedStreams: List<SearchItem> = emptyList()
)

data class AudioStream(
    val url: String = "",
    val format: String = "",
    val quality: String = "",
    val mimeType: String = "",
    val codec: String = "",
    val bitrate: Int = 0
)

data class VideoStream(
    val url: String = "",
    val format: String = "",
    val quality: String = "",
    val mimeType: String = "",
    val width: Int = 0,
    val height: Int = 0
)

data class TrendingItem(
    val url: String = "",
    val title: String = "",
    val thumbnail: String = "",
    val uploaderName: String = "",
    val uploaderUrl: String = "",
    val uploaderAvatar: String = "",
    val duration: Long = 0,
    val views: Long = 0,
    val type: String = "stream"
)

data class PlaylistData(
    val name: String = "",
    val thumbnailUrl: String = "",
    val bannerUrl: String? = null,
    val uploader: String = "",
    val uploaderUrl: String = "",
    val uploaderAvatar: String = "",
    val videos: Int = 0,
    val relatedStreams: List<SearchItem> = emptyList(),
    val nextpage: String? = null
)

data class ChannelData(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String = "",
    val bannerUrl: String = "",
    val description: String = "",
    val subscriberCount: Long = 0,
    val relatedStreams: List<SearchItem> = emptyList(),
    val nextpage: String? = null
)

data class LyricsResult(
    val id: Long = 0,
    val name: String = "",
    val trackName: String = "",
    val artistName: String = "",
    val albumName: String = "",
    val duration: Double = 0.0,
    val instrumental: Boolean = false,
    val plainLyrics: String? = null,
    val syncedLyrics: String? = null
)

data class SyncedLyricLine(
    val timeMs: Long,
    val text: String
)

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val thumbnailUrl: String,
    val duration: Long,
    val streamUrl: String? = null
)
