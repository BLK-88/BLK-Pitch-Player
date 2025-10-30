package com.musicplayer.BLKPitchPlayer.data.repository

import com.musicplayer.BLKPitchPlayer.data.local.dao.PlaylistDao
import com.musicplayer.BLKPitchPlayer.data.local.entities.PlaylistEntity
import com.musicplayer.BLKPitchPlayer.domain.model.Playlist
import com.musicplayer.BLKPitchPlayer.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implémentation du PlaylistRepository utilisant Room
 */
class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getPlaylistById(playlistId: Long): Playlist? {
        return playlistDao.getPlaylistById(playlistId)?.toDomainModel()
    }

    override suspend fun createPlaylist(playlist: Playlist): Long {
        return playlistDao.insertPlaylist(playlist.toEntity())
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistDao.updatePlaylist(playlist.toEntity())
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.deletePlaylist(playlist.toEntity())
    }

    override suspend fun deletePlaylistById(playlistId: Long) {
        playlistDao.deletePlaylistById(playlistId)
    }

    // Mappeurs
    private fun PlaylistEntity.toDomainModel(): Playlist {
        return Playlist(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
            trackIds = trackIds.split(",").mapNotNull { it.toLongOrNull() }
        )
    }

    private fun Playlist.toEntity(): PlaylistEntity {
        return PlaylistEntity(
            id = id,
            name = name,
            description = description,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
            trackIds = trackIds.joinToString(",")
        )
    }
}
