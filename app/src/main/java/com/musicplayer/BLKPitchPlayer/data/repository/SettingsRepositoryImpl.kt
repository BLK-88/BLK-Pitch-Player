package com.musicplayer.BLKPitchPlayer.data.repository

import com.musicplayer.BLKPitchPlayer.data.local.dao.TrackSettingsDao
import com.musicplayer.BLKPitchPlayer.data.local.entities.TrackSettingsEntity
import com.musicplayer.BLKPitchPlayer.domain.model.TrackSettings
import com.musicplayer.BLKPitchPlayer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implémentation du SettingsRepository utilisant Room
 */
class SettingsRepositoryImpl @Inject constructor(
    private val trackSettingsDao: TrackSettingsDao
) : SettingsRepository {

    override fun getTrackSettings(trackId: Long): Flow<TrackSettings?> {
        return trackSettingsDao.getTrackSettings(trackId).map { entity ->
            entity?.toDomainModel()
        }
    }

    override fun getFavoriteTracks(): Flow<List<TrackSettings>> {
        return trackSettingsDao.getFavoriteTracks().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun saveTrackSettings(settings: TrackSettings) {
        trackSettingsDao.insertTrackSettings(settings.toEntity())
    }

    override suspend fun updatePitch(trackId: Long, pitch: Int) {
        trackSettingsDao.updatePitch(trackId, pitch)
    }

    override suspend fun updateTempo(trackId: Long, tempo: Float) {
        trackSettingsDao.updateTempo(trackId, tempo)
    }

    override suspend fun updateLastPosition(trackId: Long, position: Long) {
        trackSettingsDao.updateLastPosition(trackId, position)
    }

    override suspend fun toggleFavorite(trackId: Long, isFavorite: Boolean) {
        trackSettingsDao.updateFavoriteStatus(trackId, isFavorite)
    }

    // Mappeurs
    private fun TrackSettingsEntity.toDomainModel(): TrackSettings {
        return TrackSettings(
            trackId = trackId,
            pitchSemitones = pitchSemitones,
            tempoMultiplier = tempoMultiplier,
            lastPosition = lastPosition,
            isFavorite = isFavorite
        )
    }

    private fun TrackSettings.toEntity(): TrackSettingsEntity {
        return TrackSettingsEntity(
            trackId = trackId,
            pitchSemitones = pitchSemitones,
            tempoMultiplier = tempoMultiplier,
            lastPosition = lastPosition,
            isFavorite = isFavorite
        )
    }
}
