package com.musicplayer.BLKPitchPlayer

import android.app.Application
import com.musicplayer.BLKPitchPlayer.BuildConfig  // ← Ajout de cet import
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe Application principale annotée avec @HiltAndroidApp
 * pour activer l'injection de dépendances Hilt dans toute l'application.
 */
@HiltAndroidApp
class BLKApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Configuration initiale de l'application
        if (BuildConfig.DEBUG) {
            // Active le StrictMode en mode DEBUG pour détecter les problèmes
            enableStrictMode()
        }
    }

    /**
     * Active le StrictMode pour détecter les opérations sur le thread principal
     */
    private fun enableStrictMode() {
        android.os.StrictMode.setThreadPolicy(
            android.os.StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )

        android.os.StrictMode.setVmPolicy(
            android.os.StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }
}
