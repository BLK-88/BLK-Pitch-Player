package com.musicplayer.BLKPitchPlayer.domain.usecase

import com.musicplayer.BLKPitchPlayer.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Use Case pour mettre à jour le pitch d'une piste
 * @param trackId ID de la piste
 * @param pitch Pitch en demi-tons (-12 à +12)
 */
class UpdatePitchUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(trackId: Long, pitch: Int) {
        // Valider que pitch est dans les limites
        if (pitch < -12 || pitch > 12) {
            throw IllegalArgumentException("Pitch doit être entre -12 et +12")
        }

        settingsRepository.updatePitch(trackId, pitch)
    }
}
