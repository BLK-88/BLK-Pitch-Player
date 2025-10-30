package com.musicplayer.BLKPitchPlayer.util

import android.text.format.DateUtils
import kotlin.math.abs

/**
 * Extensions pour formater les durées audio
 */
fun Long.formatDuration(): String {
    return if (this <= 0) {
        "00:00"
    } else {
        val seconds = (this / 1000) % 60
        val minutes = (this / (1000 * 60)) % 60
        val hours = this / (1000 * 60 * 60)

        when {
            hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
            else -> String.format("%02d:%02d", minutes, seconds)
        }
    }
}

/**
 * Extensions pour valider les paramètres audio
 */
fun Int.isValidPitch(): Boolean {
    return this in Constants.MIN_PITCH_SEMITONES..Constants.MAX_PITCH_SEMITONES
}

fun Float.isValidTempo(): Boolean {
    return this >= Constants.MIN_TEMPO_MULTIPLIER &&
            this <= Constants.MAX_TEMPO_MULTIPLIER
}

/**
 * Clamp une valeur entre min et max
 */
fun <T : Comparable<T>> T.coerceInRange(min: T, max: T): T {
    return when {
        this < min -> min
        this > max -> max
        else -> this
    }
}

/**
 * Extension pour les pourcentages
 */
fun Float.toPercentage(): String {
    return "${(this * 100).toInt()}%"
}
