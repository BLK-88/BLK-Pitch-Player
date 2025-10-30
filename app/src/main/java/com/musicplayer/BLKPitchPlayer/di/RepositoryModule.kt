package com.musicplayer.BLKPitchPlayer.di

import com.musicplayer.BLKPitchPlayer.data.repository.TrackRepositoryImpl
import com.musicplayer.BLKPitchPlayer.data.repository.PlaylistRepositoryImpl
import com.musicplayer.BLKPitchPlayer.data.repository.SettingsRepositoryImpl
import com.musicplayer.BLKPitchPlayer.domain.repository.TrackRepository
import com.musicplayer.BLKPitchPlayer.domain.repository.PlaylistRepository
import com.musicplayer.BLKPitchPlayer.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module Hilt pour lier les interfaces Repository à leurs implémentations
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTrackRepository(
        impl: TrackRepositoryImpl
    ): TrackRepository

    @Binds
    @Singleton
    abstract fun bindPlaylistRepository(
        impl: PlaylistRepositoryImpl
    ): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository
}
