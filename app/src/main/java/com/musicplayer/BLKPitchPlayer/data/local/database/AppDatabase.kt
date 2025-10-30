package com.musicplayer.BLKPitchPlayer.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.musicplayer.BLKPitchPlayer.data.local.dao.TrackDao
import com.musicplayer.BLKPitchPlayer.data.local.dao.PlaylistDao
import com.musicplayer.BLKPitchPlayer.data.local.dao.TrackSettingsDao
import com.musicplayer.BLKPitchPlayer.data.local.entities.TrackEntity
import com.musicplayer.BLKPitchPlayer.data.local.entities.PlaylistEntity
import com.musicplayer.BLKPitchPlayer.data.local.entities.TrackSettingsEntity

/**
 * Base de données Room principale de l'application
 */
@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        TrackSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun trackSettingsDao(): TrackSettingsDao
}
