package com.musicplayer.BLKPitchPlayer.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entité Room pour les paramètres personnalisés par piste
 */
@Entity(tableName = "track_settings")
data class TrackSettingsEntity(
    @PrimaryKey
    val trackId: Long,
    val pitchSemitones: Int = 0, // De -12 à +12
    val tempoMultiplier: Float = 1.0f, // De 0.5 à 2.0
    val lastPosition: Long = 0, // Position de lecture en ms
    val isFavorite: Boolean = false
)
