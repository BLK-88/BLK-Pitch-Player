package com.musicplayer.BLKPitchPlayer.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Détecte les double-clics rapides
 */
class DoubleClickHelper(private val delayMs: Long = 300) {

    private val _clickCount = MutableStateFlow(0)
    val clickCount: StateFlow<Int> = _clickCount.asStateFlow()

    private var lastClickTime = 0L

    /**
     * Appelle cette fonction à chaque clic
     * Retourne true si double-clic détecté
     */
    fun onClick(): Boolean {
        val currentTime = System.currentTimeMillis()
        val isDoubleClick = (currentTime - lastClickTime) < delayMs
        lastClickTime = currentTime

        return isDoubleClick
    }

    /**
     * Réinitialise
     */
    fun reset() {
        lastClickTime = 0L
    }
}
