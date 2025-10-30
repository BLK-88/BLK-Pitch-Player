package com.musicplayer.BLKPitchPlayer.domain.model

/**
 * Modèle métier pour les paramètres d'une piste
 */
data class TrackSettings(
    val trackId: Long,
    val pitchSemitones: Int = 0,
    val tempoMultiplier: Float = 1.0f,
    val lastPosition: Long = 0,
    val isFavorite: Boolean = false
)
