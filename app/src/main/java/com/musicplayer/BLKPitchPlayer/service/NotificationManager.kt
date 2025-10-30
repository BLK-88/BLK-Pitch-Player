package com.musicplayer.BLKPitchPlayer.service

import android.app.NotificationChannel
import android.app.NotificationManager as AndroidNotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gestionnaire des notifications pour le service de lecture
 */
@Singleton
class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as AndroidNotificationManager

    companion object {
        const val CHANNEL_ID = "blk_playback_channel"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_NAME = "BLK Pitch Player"
    }

    init {
        createNotificationChannel()
    }

    /**
     * Crée le canal de notification pour Android 8+
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                AndroidNotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications de lecture audio"
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Crée une notification de lecture
     */
    fun createPlaybackNotification(
        title: String,
        artist: String,
        albumArtUri: String? = null
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(artist)
            .setSmallIcon(android.R.drawable.ic_media_play)
            // Suppression de MediaStyle pour éviter les dépendances supplémentaires
            // Sera ajouté dans Phase 2 avec Media3
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
    }
}
