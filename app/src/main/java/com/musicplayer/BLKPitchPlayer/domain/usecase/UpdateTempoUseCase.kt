package com.musicplayer.BLKPitchPlayer.domain.usecase

import com.musicplayer.BLKPitchPlayer.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Use Case pour mettre à jour le tempo d'une piste
 * @param trackId ID de la piste
 * @param tempo Multiplicateur de tempo (0.5 à 2.0)
 */
class UpdateTempoUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(trackId: Long, tempo: Float) {
        // Valider que tempo est dans les limites
        if (tempo < 0.5f || tempo > 2.0f) {
            throw IllegalArgumentException("Tempo doit être entre 0.5 et 2.0")
        }

        settingsRepository.updateTempo(trackId, tempo)
    }
}
