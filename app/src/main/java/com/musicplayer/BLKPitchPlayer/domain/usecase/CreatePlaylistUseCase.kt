package com.musicplayer.BLKPitchPlayer.domain.usecase

import com.musicplayer.BLKPitchPlayer.domain.model.Playlist
import com.musicplayer.BLKPitchPlayer.domain.repository.PlaylistRepository
import javax.inject.Inject

/**
 * Use Case pour créer une nouvelle playlist
 */
class CreatePlaylistUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {

    suspend operator fun invoke(
        name: String,
        description: String? = null,
        trackIds: List<Long> = emptyList()
    ): Long {
        if (name.isBlank()) {
            throw IllegalArgumentException("Le nom de la playlist ne peut pas être vide")
        }

        val now = System.currentTimeMillis()
        val playlist = Playlist(
            id = 0, // Room générara l'ID
            name = name,
            description = description,
            createdAt = now,
            modifiedAt = now,
            trackIds = trackIds
        )

        return playlistRepository.createPlaylist(playlist)
    }
}
