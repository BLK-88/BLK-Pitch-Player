package com.musicplayer.BLKPitchPlayer.domain.usecase

import com.musicplayer.BLKPitchPlayer.domain.model.Track
import com.musicplayer.BLKPitchPlayer.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Case pour récupérer toutes les pistes
 */
class GetAllTracksUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {

    operator fun invoke(): Flow<List<Track>> {
        return trackRepository.getAllTracks()
    }
}
