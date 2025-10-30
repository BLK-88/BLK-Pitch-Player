package com.musicplayer.BLKPitchPlayer.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entité Room représentant une piste audio
 */
@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long, // En millisecondes
    val path: String,
    val albumArtUri: String?,
    val mimeType: String,
    val size: Long,
    val dateAdded: Long,
    val dateModified: Long
)
