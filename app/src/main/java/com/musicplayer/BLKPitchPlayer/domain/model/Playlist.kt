package com.musicplayer.BLKPitchPlayer.domain.model

/**
 * Modèle métier représentant une playlist
 */
data class Playlist(
    val id: Long,
    val name: String,
    val description: String?,
    val createdAt: Long,
    val modifiedAt: Long,
    val trackIds: List<Long>
)
