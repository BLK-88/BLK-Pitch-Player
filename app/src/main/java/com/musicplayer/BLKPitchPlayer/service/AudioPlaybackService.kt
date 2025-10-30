package com.musicplayer.BLKPitchPlayer.service

import androidx.media3.session.MediaSessionService
import android.content.Intent
import androidx.media3.session.MediaSession
import dagger.hilt.android.AndroidEntryPoint

/**
 * Service pour la lecture audio en arrière-plan utilisant Media3
 * Hérite de MediaSessionService pour les contrôles media
 */
@AndroidEntryPoint
class AudioPlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        // Configuration du service sera ajoutée dans les phases ultérieures
        // Création de MediaSession, configuration ExoPlayer, etc.
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession?.release()
    }
}
