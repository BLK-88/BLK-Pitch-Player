package com.musicplayer.BLKPitchPlayer.domain.repository

import com.musicplayer.BLKPitchPlayer.domain.model.TrackSettings
import kotlinx.coroutines.flow.Flow

/**
 * Interface du repository pour les paramètres des pistes
 */
interface SettingsRepository {

    fun getTrackSettings(trackId: Long): Flow<TrackSettings?>

    fun getFavoriteTracks(): Flow<List<TrackSettings>>

    suspend fun saveTrackSettings(settings: TrackSettings)

    suspend fun updatePitch(trackId: Long, pitch: Int)

    suspend fun updateTempo(trackId: Long, tempo: Float)

    suspend fun updateLastPosition(trackId: Long, position: Long)

    suspend fun toggleFavorite(trackId: Long, isFavorite: Boolean)
}
