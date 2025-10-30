package com.musicplayer.BLKPitchPlayer.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.musicplayer.BLKPitchPlayer.domain.model.Track
import com.musicplayer.BLKPitchPlayer.presentation.ui.components.TrackItem
import com.musicplayer.BLKPitchPlayer.presentation.viewmodel.LibraryViewModel

/**
 * Écran affichant la bibliothèque audio
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    onTrackClick: (Track, List<Track>) -> Unit = { _, _ -> }
) {
    val tracks by libraryViewModel.tracks.collectAsStateWithLifecycle()
    val isLoading by libraryViewModel.isLoading.collectAsStateWithLifecycle()
    val error by libraryViewModel.error.collectAsStateWithLifecycle()

    // Scanner automatiquement au démarrage si la liste est vide
    LaunchedEffect(Unit) {
        if (tracks.isEmpty() && !isLoading) {
            libraryViewModel.scanLibrary()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bibliothèque Audio") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = { libraryViewModel.scanLibrary() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Actualiser"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                // Chargement en cours
                isLoading -> {
                    LoadingContent()
                }

                // Erreur
                error != null -> {
                    ErrorContent(
                        error = error!!,
                        onRetry = {
                            libraryViewModel.clearError()
                            libraryViewModel.scanLibrary()
                        }
                    )
                }

                // Liste vide
                tracks.isEmpty() -> {
                    EmptyContent(
                        onScan = { libraryViewModel.scanLibrary() }
                    )
                }

                // Afficher les pistes
                else -> {
                    TracksContent(
                        tracks = tracks,
                        onTrackClick = onTrackClick
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Scan de la bibliothèque en cours...",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "❌ Erreur",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onRetry) {
            Text("Réessayer")
        }
    }
}

@Composable
fun EmptyContent(
    onScan: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎵",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Aucune piste trouvée",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Scannez votre bibliothèque pour découvrir vos musiques",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onScan) {
            Text("Scanner la bibliothèque")
        }
    }
}

@Composable
fun TracksContent(
    tracks: List<Track>,
    onTrackClick: (Track, List<Track>) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // En-tête avec nombre de pistes
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = "${tracks.size} piste(s) trouvée(s)",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Liste des pistes
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = tracks,
                key = { track -> track.id }
            ) { track ->
                TrackItem(
                    track = track,
                    onTrackClick = { clickedTrack ->
                        // Passer la piste ET la liste complète
                        onTrackClick(clickedTrack, tracks)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}
