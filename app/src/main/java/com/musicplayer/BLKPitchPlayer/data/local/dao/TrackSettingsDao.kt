package com.musicplayer.BLKPitchPlayer.data.local.dao

import androidx.room.*
import com.musicplayer.BLKPitchPlayer.data.local.entities.TrackSettingsEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO pour les paramètres personnalisés des pistes
 */
@Dao
interface TrackSettingsDao {

    @Query("SELECT * FROM track_settings WHERE trackId = :trackId")
    fun getTrackSettings(trackId: Long): Flow<TrackSettingsEntity?>

    @Query("SELECT * FROM track_settings WHERE isFavorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackSettingsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackSettings(settings: TrackSettingsEntity)

    @Update
    suspend fun updateTrackSettings(settings: TrackSettingsEntity)

    @Query("UPDATE track_settings SET pitchSemitones = :pitch WHERE trackId = :trackId")
    suspend fun updatePitch(trackId: Long, pitch: Int)

    @Query("UPDATE track_settings SET tempoMultiplier = :tempo WHERE trackId = :trackId")
    suspend fun updateTempo(trackId: Long, tempo: Float)

    @Query("UPDATE track_settings SET lastPosition = :position WHERE trackId = :trackId")
    suspend fun updateLastPosition(trackId: Long, position: Long)

    @Query("UPDATE track_settings SET isFavorite = :isFavorite WHERE trackId = :trackId")
    suspend fun updateFavoriteStatus(trackId: Long, isFavorite: Boolean)

    @Delete
    suspend fun deleteTrackSettings(settings: TrackSettingsEntity)
}
