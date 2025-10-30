package com.musicplayer.BLKPitchPlayer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Graphe de navigation de l'application
 * À compléter lors de l'implémentation des écrans
 */
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            // HomeScreen()
        }

        composable(Routes.LIBRARY) {
            // LibraryScreen()
        }

        composable(Routes.PLAYLISTS) {
            // PlaylistsScreen()
        }

        composable(Routes.SETTINGS) {
            // SettingsScreen()
        }
    }
}
