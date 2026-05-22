package com.musicify.app.player

import android.content.Context
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
    val currentPosition: Long = 0L
)

@Singleton
class PlayerManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _nowPlaying = MutableStateFlow(NowPlaying())
    val nowPlaying: StateFlow<NowPlaying> = _nowPlaying.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var player: ExoPlayer? = null

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
            isPlaying = false
        )
        _isLoading.value = true

        scope.launch {
            repository.getStreamUrl(videoId)
                .onSuccess { url ->
                    val p = getPlayer()
                    val mediaItem = MediaItem.Builder()
                        .setUri(url)
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
                    _isLoading.value = false
                }
                .onFailure {
                    _isLoading.value = false
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
