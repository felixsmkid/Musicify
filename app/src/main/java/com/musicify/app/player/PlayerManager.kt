package com.musicify.app.player

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.musicify.app.data.repository.MusicRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

data class NowPlaying(
    val videoId: String = "",
    val title: String = "",
    val artist: String = "",
    val thumbnailUrl: String = "",
    val duration: Long = 0L,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val error: String? = null
)

@Singleton
class PlayerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val tag = "PlayerManager"

    private val _nowPlaying = MutableStateFlow(NowPlaying())
    val nowPlaying: StateFlow<NowPlaying> = _nowPlaying.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var player: ExoPlayer? = null

    private val pipedInstances = listOf(
        "https://pipedapi.kavin.rocks",
        "https://pipedapi-libre.kavin.rocks",
        "https://pipedapi.adminforge.de",
        "https://api.piped.yt",
        "https://pipedapi.r4fo.com"
    )

    private fun getPlayer(): ExoPlayer {
        if (player == null) {
            player = ExoPlayer.Builder(context).build().apply {
                addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        _nowPlaying.value = _nowPlaying.value.copy(isPlaying = isPlaying)
                    }

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        if (playbackState == Player.STATE_ENDED) {
                            _nowPlaying.value = _nowPlaying.value.copy(isPlaying = false)
                        }
                    }

                    override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                        Log.e(tag, "Playback error: ${error.message}")
                        _nowPlaying.value = _nowPlaying.value.copy(
                            isPlaying = false,
                            error = "Playback error"
                        )
                        _isLoading.value = false
                        scope.launch(Dispatchers.Main) {
                            Toast.makeText(context, "Playback failed, trying next source...", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
        return player!!
    }

    fun play(videoId: String, title: String, artist: String, thumbnailUrl: String, durationSec: Long) {
        _nowPlaying.value = NowPlaying(
            videoId = videoId,
            title = title,
            artist = artist,
            thumbnailUrl = thumbnailUrl,
            duration = durationSec * 1000,
            isPlaying = false,
            error = null
        )
        _isLoading.value = true

        scope.launch {
            var streamUrl: String? = null

            // Try 1: InnerTube ANDROID_MUSIC client
            Log.d(tag, "Trying InnerTube for $videoId")
            repository.getStreamUrl(videoId)
                .onSuccess { url ->
                    Log.d(tag, "InnerTube success: ${url.take(50)}")
                    streamUrl = url
                }
                .onFailure { e ->
                    Log.w(tag, "InnerTube failed: ${e.message}")
                }

            // Try 2: Piped instances
            if (streamUrl == null) {
                for (instance in pipedInstances) {
                    Log.d(tag, "Trying Piped: $instance")
                    repository.getStreamFromPiped(instance, videoId)
                        .onSuccess { url ->
                            Log.d(tag, "Piped success from $instance")
                            streamUrl = url
                        }
                        .onFailure { e ->
                            Log.w(tag, "Piped $instance failed: ${e.message}")
                        }
                    if (streamUrl != null) break
                }
            }

            // Play or show error
            if (streamUrl != null) {
                try {
                    val p = getPlayer()
                    val mediaItem = MediaItem.Builder()
                        .setUri(streamUrl)
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .setTitle(title)
                                .setArtist(artist)
                                .build()
                        )
                        .build()
                    p.setMediaItem(mediaItem)
                    p.prepare()
                    p.play()
                } catch (e: Exception) {
                    Log.e(tag, "Player error: ${e.message}")
                    _nowPlaying.value = _nowPlaying.value.copy(error = "Failed to play")
                }
                _isLoading.value = false
            } else {
                _isLoading.value = false
                _nowPlaying.value = _nowPlaying.value.copy(
                    error = "No stream available"
                )
                scope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Could not load audio. Try another song.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun togglePlayPause() {
        player?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    fun seekTo(positionMs: Long) {
        player?.seekTo(positionMs)
    }

    fun getCurrentPosition(): Long = player?.currentPosition ?: 0L

    fun release() {
        player?.release()
        player = null
    }
}
