package com.musicplayer.BLKPitchPlayer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicplayer.BLKPitchPlayer.domain.model.Track
import com.musicplayer.BLKPitchPlayer.domain.repository.TrackRepository
import com.musicplayer.BLKPitchPlayer.domain.usecase.ScanMediaLibraryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel pour gérer l'état de la bibliothèque audio
 */
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
    private val scanMediaLibraryUseCase: ScanMediaLibraryUseCase
) : ViewModel() {

    // État de chargement
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Liste des pistes
    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    // Message d'erreur
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Piste actuellement sélectionnée
    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack.asStateFlow()

    init {
        // Observer les changements de pistes
        viewModelScope.launch {
            trackRepository.getAllTracks().collect { trackList ->
                _tracks.value = trackList
            }
        }
    }

    /**
     * Scanne la bibliothèque audio et charge les pistes
     */
    fun scanLibrary() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                scanMediaLibraryUseCase()
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = "Erreur : ${e.message}"
                _isLoading.value = false
            }
        }
    }

    /**
     * Sélectionne une piste
     */
    fun selectTrack(track: Track) {
        _currentTrack.value = track
    }

    /**
     * Efface le message d'erreur
     */
    fun clearError() {
        _error.value = null
    }
}
