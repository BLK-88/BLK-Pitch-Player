package com.musicplayer.BLKPitchPlayer.service

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.musicplayer.BLKPitchPlayer.domain.model.Track
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestionnaire de la lecture audio avec ExoPlayer
 */
@Singleton
class AudioPlaybackManager @Inject constructor(
    @ApplicationContext private val context: Context
) : Player.Listener {

    private val _player = MutableStateFlow<ExoPlayer?>(null)
    val player: StateFlow<ExoPlayer?> = _player.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack.asStateFlow()

    // Stocker l'index actuel
    private val _currentMediaItemIndex = MutableStateFlow(0)
    val currentMediaItemIndex: StateFlow<Int> = _currentMediaItemIndex.asStateFlow()

    init {
        initializePlayer()
    }

    /**
     * Initialise ExoPlayer
     */
    private fun initializePlayer() {
        try {
            val exoPlayer = ExoPlayer.Builder(context).build()
            exoPlayer.addListener(this)
            _player.value = exoPlayer
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Joue une piste seule
     */
    fun playTrack(track: Track) {
        try {
            _currentTrack.value = track
            _currentMediaItemIndex.value = 0

            val mediaItem = MediaItem.fromUri(track.path)
            _player.value?.apply {
                setMediaItem(mediaItem)
                prepare()
                play()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Joue une piste avec queue complète
     * Permet la navigation entre pistes avec skipNext/skipPrevious
     */
    fun playTrackWithQueue(track: Track, allTracks: List<Track>) {
        try {
            _currentTrack.value = track

            // Créer les items pour toutes les pistes
            val mediaItems = allTracks.map { MediaItem.fromUri(it.path) }

            _player.value?.apply {
                // Ajouter toutes les pistes
                setMediaItems(mediaItems)

                // Trouver l'index de la piste actuelle
                val trackIndex = allTracks.indexOfFirst { it.id == track.id }

                // Se positionner à la piste actuelle
                if (trackIndex >= 0) {
                    seekToDefaultPosition(trackIndex)
                    _currentMediaItemIndex.value = trackIndex
                }

                prepare()
                play()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Met en pause
     */
    fun pause() {
        _player.value?.pause()
    }

    /**
     * Reprend la lecture
     */
    fun resume() {
        _player.value?.play()
    }

    /**
     * Bascule Play/Pause
     */
    fun togglePlayPause() {
        _player.value?.apply {
            if (isPlaying) {
                pause()
            } else {
                play()
            }
        }
    }

    /**
     * Passe à la piste suivante
     * Fonctionne uniquement si une queue a été définie
     */
    fun skipNext() {
        _player.value?.seekToNext()
    }

    /**
     * Revient à la piste précédente
     * Fonctionne uniquement si une queue a été définie
     */
    fun skipPrevious() {
        _player.value?.seekToPrevious()
    }

    /**
     * Change la position
     */
    fun seek(position: Long) {
        _player.value?.seekTo(position)
    }

    /**
     * Arrête la lecture et nettoie
     */
    fun stop() {
        _player.value?.apply {
            stop()
            clearMediaItems()
        }
    }

    /**
     * Change le tempo (vitesse) - ExoPlayer supporte nativement
     * @param multiplier : 0.5 à 2.0
     */
    fun setTempo(multiplier: Float) {
        println("⏱️ Tempo changé : ${String.format("%.1f", multiplier)}x")
        _player.value?.apply {
            setPlaybackSpeed(multiplier)
        }
    }

    /**
     * Simule un changement de pitch (affichage UI)
     * NOTE: Sans SoundTouch, c'est juste un affichage
     * @param semitones : -12 à +12
     */
    fun setPitch(semitones: Int) {
        println("🎼 Pitch changé (affichage) : $semitones semitones")
        // TODO: Intégrer SoundTouch réel plus tard
        // Pour maintenant : affichage seulement
    }

    /**
     * Réinitialise pitch et tempo
     */
    fun resetPitchAndTempo() {
        println("🔄 Audio réinitialisé")
        _player.value?.apply {
            setPlaybackSpeed(1.0f)
        }
    }

    /**
     * Libère les ressources
     */
    fun release() {
        _player.value?.apply {
            removeListener(this@AudioPlaybackManager)
            release()
        }
        _player.value = null
    }

    // Implémentation Player.Listener
    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_BUFFERING -> {
                // Buffering
            }
            Player.STATE_READY -> {
                _duration.value = _player.value?.duration ?: 0L
            }
            Player.STATE_ENDED -> {
                // Lecture terminée
            }
            Player.STATE_IDLE -> {
                // Inactif
            }
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
    }

    override fun onPositionDiscontinuity(
        oldPosition: Player.PositionInfo,
        newPosition: Player.PositionInfo,
        reason: Int
    ) {
        _currentPosition.value = newPosition.positionMs

        // 🔑 IMPORTANT : Tracker le changement d'index
        if (oldPosition.mediaItemIndex != newPosition.mediaItemIndex) {
            _currentMediaItemIndex.value = newPosition.mediaItemIndex
            println("📍 Index changé : ${oldPosition.mediaItemIndex} → ${newPosition.mediaItemIndex}")
        }
    }
}
