package com.musicplayer.BLKPitchPlayer.domain.model

/**
 * Modèle pour les données du visualiseur audio
 */
data class AudioVisualizerData(
    val waveform: ByteArray,
    val fft: ByteArray,
    val captureRate: Int,
    val samplingRate: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AudioVisualizerData

        if (!waveform.contentEquals(other.waveform)) return false
        if (!fft.contentEquals(other.fft)) return false
        if (captureRate != other.captureRate) return false
        if (samplingRate != other.samplingRate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = waveform.contentHashCode()
        result = 31 * result + fft.contentHashCode()
        result = 31 * result + captureRate
        result = 31 * result + samplingRate
        return result
    }
}
