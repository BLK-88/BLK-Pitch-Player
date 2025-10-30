package com.musicplayer.BLKPitchPlayer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.BLKPitchPlayer.domain.model.Track
import com.musicplayer.BLKPitchPlayer.domain.repository.SettingsRepository
import com.musicplayer.BLKPitchPlayer.service.AudioPlaybackManager
import com.musicplayer.BLKPitchPlayer.util.Constants
import com.musicplayer.BLKPitchPlayer.util.DoubleClickHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour gérer l'état de la lecture audio
 */
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val audioPlaybackManager: AudioPlaybackManager,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    // État de lecture
    val isPlaying: StateFlow<Boolean> = audioPlaybackManager.isPlaying

    // Piste actuelle
    val currentTrack: StateFlow<Track?> = audioPlaybackManager.currentTrack

    // Position actuelle (mise à jour depuis le player)
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    // Durée totale en ms
    val duration: StateFlow<Long> = audioPlaybackManager.duration

    // Pitch actuel (-12 à +12 demi-tons)
    private val _pitch = MutableStateFlow(0)
    val pitch: StateFlow<Int> = _pitch.asStateFlow()

    // Tempo actuel (0.5 à 2.0)
    private val _tempo = MutableStateFlow(1.0f)
    val tempo: StateFlow<Float> = _tempo.asStateFlow()

    // List des pistes en queue
    private val _trackQueue = MutableStateFlow<List<Track>>(emptyList())
    val trackQueue: StateFlow<List<Track>> = _trackQueue.asStateFlow()

    // Index actuel dans la queue (lié à AudioPlaybackManager)
    val currentTrackIndex: StateFlow<Int> = audioPlaybackManager.currentMediaItemIndex

    // Helpers pour détecter double-clic
    private val skipPrevDoubleClickHelper = DoubleClickHelper(delayMs = 300)
    private val skipNextDoubleClickHelper = DoubleClickHelper(delayMs = 300)

    init {
        // Mettre à jour la position automatiquement toutes les 100ms
        viewModelScope.launch {
            while (true) {
                delay(100)
                audioPlaybackManager.player.value?.let { player ->
                    _currentPosition.value = player.currentPosition
                }
            }
        }
    }

    /**
     * Joue une piste
     */
    fun playTrack(track: Track) {
        audioPlaybackManager.playTrack(track)
    }

    /**
     * Joue une piste avec la queue complète
     */
    fun playTrackWithQueue(track: Track, allTracks: List<Track>) {
        // Sauvegarder la queue
        _trackQueue.value = allTracks

        // Lancer la lecture avec queue
        audioPlaybackManager.playTrackWithQueue(track, allTracks)
    }

    /**
     * Met en pause
     */
    fun pause() {
        audioPlaybackManager.pause()
    }

    /**
     * Reprend la lecture
     */
    fun resume() {
        audioPlaybackManager.resume()
    }

    /**
     * Bascule entre Play/Pause
     */
    fun togglePlayPause() {
        audioPlaybackManager.togglePlayPause()
    }

    /**
     * Passe à la piste précédente
     * 1 clic = retour au début
     * 2 clics rapides (< 300ms) = piste précédente
     */
    fun skipPrevious() {
        val isDoubleClick = skipPrevDoubleClickHelper.onClick()

        if (isDoubleClick) {
            // Double-clic : aller à la piste précédente
            audioPlaybackManager.skipPrevious()
            println("⏮️ Double-clic : Piste précédente")
        } else {
            // Simple clic : retour au début
            seek(0L)
            println("⏮️ Simple clic : Retour début")
        }
    }

    /**
     * Passe à la piste suivante
     * 1 clic = piste suivante
     */
    fun skipNext() {
        val isDoubleClick = skipNextDoubleClickHelper.onClick()

        if (isDoubleClick) {
            // Double-clic : rien
            println("⏭️ Double-clic détecté (pas d'action)")
        } else {
            // Simple clic : aller à la piste suivante
            audioPlaybackManager.skipNext()
            println("⏭️ Simple clic : Piste suivante")
        }
    }

    /**
     * Change la position de lecture
     */
    fun seek(position: Long) {
        audioPlaybackManager.seek(position)
        _currentPosition.value = position
    }

    /**
     * Met à jour le pitch
     */
    fun setPitch(pitchSemitones: Int) {
        if (pitchSemitones < Constants.MIN_PITCH_SEMITONES ||
            pitchSemitones > Constants.MAX_PITCH_SEMITONES) {
            return
        }

        _pitch.value = pitchSemitones
        audioPlaybackManager.setPitch(pitchSemitones)

        currentTrack.value?.let { track ->
            viewModelScope.launch {
                settingsRepository.updatePitch(track.id, pitchSemitones)
            }
        }
    }

    /**
     * Met à jour le tempo
     */
    fun setTempo(tempoMultiplier: Float) {
        if (tempoMultiplier < Constants.MIN_TEMPO_MULTIPLIER ||
            tempoMultiplier > Constants.MAX_TEMPO_MULTIPLIER) {
            return
        }

        _tempo.value = tempoMultiplier
        audioPlaybackManager.setTempo(tempoMultiplier)

        currentTrack.value?.let { track ->
            viewModelScope.launch {
                settingsRepository.updateTempo(track.id, tempoMultiplier)
            }
        }
    }

    /**
     * Réinitialise les paramètres audio
     */
    fun resetAudioSettings() {
        _pitch.value = 0
        _tempo.value = 1.0f

        audioPlaybackManager.resetPitchAndTempo()

        currentTrack.value?.let { track ->
            viewModelScope.launch {
                settingsRepository.updatePitch(track.id, 0)
                settingsRepository.updateTempo(track.id, 1.0f)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
