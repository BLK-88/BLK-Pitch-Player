package com.musicplayer.BLKPitchPlayer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.musicplayer.BLKPitchPlayer.domain.model.Track
import com.musicplayer.BLKPitchPlayer.presentation.ui.screens.LibraryScreen
import com.musicplayer.BLKPitchPlayer.presentation.ui.screens.PlayerScreen
import com.musicplayer.BLKPitchPlayer.presentation.ui.theme.BLKPitchPlayerTheme
import com.musicplayer.BLKPitchPlayer.presentation.viewmodel.PermissionViewModel
import com.musicplayer.BLKPitchPlayer.presentation.viewmodel.PermissionState
import com.musicplayer.BLKPitchPlayer.presentation.viewmodel.PlayerViewModel
import com.musicplayer.BLKPitchPlayer.presentation.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BLKPitchPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent(
    permissionViewModel: PermissionViewModel = hiltViewModel()
) {
    val permissionState by permissionViewModel.permissionState.collectAsStateWithLifecycle()

    // Lanceur de permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        permissionViewModel.checkPermissions()
    }

    // Fonction pour demander les permissions
    val requestPermissions = {
        val permissions = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            arrayOf(android.Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionLauncher.launch(permissions)
    }

    // Vérifier au démarrage
    LaunchedEffect(Unit) {
        permissionViewModel.checkPermissions()
    }

    // Affichage selon l'état
    when (permissionState) {
        is PermissionState.Granted -> {
            WelcomeScreen()
        }
        is PermissionState.NotGranted -> {
            PermissionDeniedScreen(onRequestPermissions = requestPermissions)
        }
        is PermissionState.Initial -> {
            LoadingScreen()
        }
        is PermissionState.PermissionRequested -> {
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(500)
                permissionViewModel.checkPermissions()
            }
            LoadingScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen() {
    // États de navigation
    var showLibrary by remember { mutableStateOf(false) }
    var showPlayer by remember { mutableStateOf(false) }

    // ViewModels
    val playerViewModel: PlayerViewModel = hiltViewModel()
    val libraryViewModel: LibraryViewModel = hiltViewModel()

    when {
        showPlayer -> {
            // Afficher PlayerScreen
            PlayerScreen(
                playerViewModel = playerViewModel,
                onBackClick = {
                    showPlayer = false
                }
            )
        }
        showLibrary -> {
            // Récupérer la piste en cours et la liste complète
            val currentTrack by playerViewModel.currentTrack.collectAsStateWithLifecycle()
            val tracks by libraryViewModel.tracks.collectAsStateWithLifecycle()

            // Afficher LibraryScreen
            LibraryScreen(
                onTrackClick = { track: Track, allTracks: List<Track> ->
                    // Si c'est la même piste, juste ouvrir le player
                    if (currentTrack?.id == track.id) {
                        showPlayer = true
                        println("▶️ Ouverture du player pour : ${track.title}")
                    } else {
                        // Charger la queue complète pour permettre skipNext/skipPrevious
                        if (allTracks.isNotEmpty()) {
                            playerViewModel.playTrackWithQueue(track, allTracks)
                        } else if (tracks.isNotEmpty()) {
                            // Fallback : utiliser la liste de LibraryViewModel
                            playerViewModel.playTrackWithQueue(track, tracks)
                        } else {
                            // Sinon, lancer la piste seule
                            playerViewModel.playTrack(track)
                        }
                        showPlayer = true
                        println("▶️ Lecture en cours : ${track.title} (avec queue)")
                    }
                }
            )
        }
        else -> {
            // Afficher l'écran de bienvenue
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("BLK Pitch Player") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "✅ Permissions Accordées !",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Bienvenue sur BLK Pitch Player",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "🎉 Phase 2 - COMPLÈTE 100% ! 🎉",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("✅ Gestion permissions runtime")
                            Text("✅ ViewModels StateFlow")
                            Text("✅ Scan MediaStore")
                            Text("✅ LibraryScreen")
                            Text("✅ Lecture audio (ExoPlayer)")
                            Text("✅ PlayerScreen + contrôles")
                            Text("✅ Navigation fluide")
                            Text("✅ Double-clic détecté")
                            Text("✅ Navigation entre pistes")
                            Text("✅ Architecture MVVM + Clean")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "→ Cliquez pour jouer votre musique !",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Bouton pour aller vers la bibliothèque
                    Button(
                        onClick = { showLibrary = true },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LibraryMusic,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Accéder à la bibliothèque")
                    }
                }
            }
        }
    }
}

@Composable
fun PermissionDeniedScreen(onRequestPermissions: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎵 Permissions Requises",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pour accéder à votre bibliothèque audio, " +
                    "nous avons besoin de votre autorisation.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRequestPermissions,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Accorder les permissions")
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Vérification des permissions...",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
