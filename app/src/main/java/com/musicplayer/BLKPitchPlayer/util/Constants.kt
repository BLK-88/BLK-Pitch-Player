package com.musicplayer.BLKPitchPlayer.util

/**
 * Constantes globales de l'application
 */
object Constants {

    // Limites Audio
    const val MIN_PITCH_SEMITONES = -12
    const val MAX_PITCH_SEMITONES = 12
    const val MIN_TEMPO_MULTIPLIER = 0.5f
    const val MAX_TEMPO_MULTIPLIER = 2.0f

    // Base de données
    const val DATABASE_NAME = "blk_pitch_player_database"

    // Notifications
    const val NOTIFICATION_CHANNEL_ID = "blk_playback_channel"
    const val NOTIFICATION_ID = 1

    // Durée minimale de piste (ms)
    const val MIN_TRACK_DURATION = 1000L

    // MediaStore
    const val SCOPED_STORAGE_MIN_SDK = 30

    // Préférences
    const val PREF_CURRENT_TRACK_ID = "current_track_id"
    const val PREF_CURRENT_POSITION = "current_position"
}
