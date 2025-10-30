package com.musicplayer.BLKPitchPlayer.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.musicplayer.BLKPitchPlayer.util.Constants
import com.musicplayer.BLKPitchPlayer.util.toPercentage

/**
 * Composant pour contrôler le tempo (vitesse de lecture)
 */
@Composable
fun TempoControl(
    currentTempo: Float = 1.0f,
    modifier: Modifier = Modifier,
    onTempoChange: (Float) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Titre
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tempo (Vitesse)",
                style = MaterialTheme.typography.titleMedium
            )

            // Affichage de la valeur actuelle
            Text(
                text = currentTempo.toPercentage(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Slider
        Slider(
            value = currentTempo,
            onValueChange = { onTempoChange(it) },
            valueRange = Constants.MIN_TEMPO_MULTIPLIER..Constants.MAX_TEMPO_MULTIPLIER,
            modifier = Modifier.fillMaxWidth(),
            steps = 14 // De 0.5 à 2.0 avec pas de 0.1
        )

        // Aide textuelle
        Text(
            text = "Ajustez la vitesse de lecture de 0.5x à 2.0x",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
