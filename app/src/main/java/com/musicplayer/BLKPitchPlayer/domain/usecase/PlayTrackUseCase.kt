package com.musicplayer.BLKPitchPlayer.domain.usecase

import com.musicplayer.BLKPitchPlayer.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Use Case pour démarrer la lecture d'une piste
 */
class PlayTrackUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(trackId: Long) {
        // Logique pour démarrer la lecture
        // À implémenter avec AudioPlaybackService
    }
}
