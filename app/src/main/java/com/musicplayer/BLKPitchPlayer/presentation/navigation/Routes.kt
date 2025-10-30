package com.musicplayer.BLKPitchPlayer.presentation.navigation

/**
 * Définition des routes de navigation de l'application
 */
object Routes {
    const val HOME = "home"
    const val PLAYER = "player/{trackId}"
    const val LIBRARY = "library"
    const val PLAYLISTS = "playlists"
    const val PLAYLIST_DETAIL = "playlist/{playlistId}"
    const val SETTINGS = "settings"

    fun playerRoute(trackId: Long) = "player/$trackId"
    fun playlistDetailRoute(playlistId: Long) = "playlist/$playlistId"
}
