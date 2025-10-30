package com.musicplayer.BLKPitchPlayer.di

import android.content.Context
import androidx.room.Room
import com.musicplayer.BLKPitchPlayer.data.local.database.AppDatabase
import com.musicplayer.BLKPitchPlayer.data.local.dao.TrackDao
import com.musicplayer.BLKPitchPlayer.data.local.dao.PlaylistDao
import com.musicplayer.BLKPitchPlayer.data.local.dao.TrackSettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module Hilt pour la configuration de la base de données Room
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Fournit l'instance unique de la base de données
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "blk_pitch_player_database"
        )
            .fallbackToDestructiveMigration() // À modifier en production
            .build()
    }

    /**
     * Fournit le TrackDao
     */
    @Provides
    @Singleton
    fun provideTrackDao(database: AppDatabase): TrackDao {
        return database.trackDao()
    }

    /**
     * Fournit le PlaylistDao
     */
    @Provides
    @Singleton
    fun providePlaylistDao(database: AppDatabase): PlaylistDao {
        return database.playlistDao()
    }

    /**
     * Fournit le TrackSettingsDao
     */
    @Provides
    @Singleton
    fun provideTrackSettingsDao(database: AppDatabase): TrackSettingsDao {
        return database.trackSettingsDao()
    }
}
