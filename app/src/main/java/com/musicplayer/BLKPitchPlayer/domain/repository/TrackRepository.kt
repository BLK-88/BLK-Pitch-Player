package com.musicplayer.BLKPitchPlayer.domain.repository

import com.musicplayer.BLKPitchPlayer.domain.model.Track
import kotlinx.coroutines.flow.Flow

/**
 * Interface du repository pour les pistes audio
 */
interface TrackRepository {

    fun getAllTracks(): Flow<List<Track>>

    suspend fun getTrackById(trackId: Long): Track?

    fun getTracksByArtist(artist: String): Flow<List<Track>>

    fun getTracksByAlbum(album: String): Flow<List<Track>>

    suspend fun insertTrack(track: Track)

    suspend fun insertTracks(tracks: List<Track>)

    suspend fun deleteTrack(track: Track)

    suspend fun deleteAllTracks()

    fun getTracksCount(): Flow<Int>
}
