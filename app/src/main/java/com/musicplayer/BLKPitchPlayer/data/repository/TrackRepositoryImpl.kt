package com.musicplayer.BLKPitchPlayer.data.repository

import com.musicplayer.BLKPitchPlayer.data.local.dao.TrackDao
import com.musicplayer.BLKPitchPlayer.data.local.entities.TrackEntity
import com.musicplayer.BLKPitchPlayer.domain.model.Track
import com.musicplayer.BLKPitchPlayer.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implémentation du TrackRepository utilisant Room
 */
class TrackRepositoryImpl @Inject constructor(
    private val trackDao: TrackDao
) : TrackRepository {

    override fun getAllTracks(): Flow<List<Track>> {
        return trackDao.getAllTracks().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getTrackById(trackId: Long): Track? {
        return trackDao.getTrackById(trackId)?.toDomainModel()
    }

    override fun getTracksByArtist(artist: String): Flow<List<Track>> {
        return trackDao.getTracksByArtist(artist).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getTracksByAlbum(album: String): Flow<List<Track>> {
        return trackDao.getTracksByAlbum(album).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun insertTrack(track: Track) {
        trackDao.insertTrack(track.toEntity())
    }

    override suspend fun insertTracks(tracks: List<Track>) {
        trackDao.insertTracks(tracks.map { it.toEntity() })
    }

    override suspend fun deleteTrack(track: Track) {
        trackDao.deleteTrack(track.toEntity())
    }

    override suspend fun deleteAllTracks() {
        trackDao.deleteAllTracks()
    }

    override fun getTracksCount(): Flow<Int> {
        return trackDao.getTracksCount()
    }

    // Mappeurs entre entités et modèles de domaine
    private fun TrackEntity.toDomainModel(): Track {
        return Track(
            id = id,
            title = title,
            artist = artist,
            album = album,
            duration = duration,
            path = path,
            albumArtUri = albumArtUri,
            mimeType = mimeType,
            size = size,
            dateAdded = dateAdded,
            dateModified = dateModified
        )
    }

    private fun Track.toEntity(): TrackEntity {
        return TrackEntity(
            id = id,
            title = title,
            artist = artist,
            album = album,
            duration = duration,
            path = path,
            albumArtUri = albumArtUri,
            mimeType = mimeType,
            size = size,
            dateAdded = dateAdded,
            dateModified = dateModified
        )
    }
}
