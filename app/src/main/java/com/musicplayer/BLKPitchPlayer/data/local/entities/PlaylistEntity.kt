package com.musicplayer.BLKPitchPlayer.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entité Room représentant une playlist
 */
@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String?,
    val createdAt: Long,
    val modifiedAt: Long,
    val trackIds: String // Liste d'IDs séparés par des virgules
)
