package com.musicplayer.BLKPitchPlayer.domain.usecase

import com.musicplayer.BLKPitchPlayer.data.source.MediaStoreDataSource
import com.musicplayer.BLKPitchPlayer.domain.repository.TrackRepository
import javax.inject.Inject

/**
 * Use Case pour scanner la bibliothèque audio du périphérique
 */
class ScanMediaLibraryUseCase @Inject constructor(
    private val mediaStoreDataSource: MediaStoreDataSource,
    private val trackRepository: TrackRepository
) {

    suspend operator fun invoke() {
        try {
            // Récupérer toutes les pistes du MediaStore
            val tracks = mediaStoreDataSource.getAllAudioTracks()

            // Effacer les anciennes pistes et insérer les nouvelles
            trackRepository.deleteAllTracks()
            trackRepository.insertTracks(tracks)
        } catch (e: Exception) {
            throw ScanException("Erreur lors du scan de la bibliothèque: ${e.message}")
        }
    }
}

/**
 * Exception personnalisée pour les erreurs de scan
 */
class ScanException(message: String, cause: Throwable? = null) : Exception(message, cause)
