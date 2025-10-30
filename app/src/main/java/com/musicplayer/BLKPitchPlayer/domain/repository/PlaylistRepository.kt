package com.musicplayer.BLKPitchPlayer.domain.repository

import com.musicplayer.BLKPitchPlayer.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

/**
 * Interface du repository pour les playlists
 */
interface PlaylistRepository {

    fun getAllPlaylists(): Flow<List<Playlist>>

    suspend fun getPlaylistById(playlistId: Long): Playlist?

    suspend fun createPlaylist(playlist: Playlist): Long

    suspend fun updatePlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

    suspend fun deletePlaylistById(playlistId: Long)
}
