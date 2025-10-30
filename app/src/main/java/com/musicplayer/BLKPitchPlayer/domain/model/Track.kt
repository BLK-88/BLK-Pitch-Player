package com.musicplayer.BLKPitchPlayer.domain.model

/**
 * Modèle métier représentant une piste audio
 */
data class Track(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val path: String,
    val albumArtUri: String?,
    val mimeType: String,
    val size: Long,
    val dateAdded: Long,
    val dateModified: Long
)
