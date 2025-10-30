package com.musicplayer.BLKPitchPlayer.util

import android.content.Context
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Scanner MediaStore helper pour requêtes personnalisées
 */
@Singleton
class MediaStoreScanner @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * Obtient le URI d'une piste par son ID
     */
    fun getTrackUri(trackId: Long) =
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.buildUpon()
            .appendPath(trackId.toString())
            .build()

    /**
     * Obtient le URI d'une couverture d'album
     */
    fun getAlbumArtUri(albumId: Long) =
        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI.buildUpon()
            .appendPath(albumId.toString())
            .build()
}
