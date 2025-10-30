package com.musicplayer.BLKPitchPlayer.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module Hilt pour les Use Cases
 * Les Use Cases seront injectés directement via leur constructeur
 * grâce à @Inject, donc pas besoin de @Provides ici
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // Les Use Cases utiliseront @Inject dans leur constructeur
    // Pas de configuration supplémentaire nécessaire ici
}
