package com.musicplayer.BLKPitchPlayer.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.musicplayer.BLKPitchPlayer.util.Constants

/**
 * Composant pour contrôler le pitch (hauteur tonale) d'une piste
 */
@Composable
fun PitchControl(
    currentPitch: Int = 0,
    modifier: Modifier = Modifier,
    onPitchChange: (Int) -> Unit = {}
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
                text = "Pitch (Hauteur Tonale)",
                style = MaterialTheme.typography.titleMedium
            )

            // Affichage de la valeur actuelle
            Text(
                text = "${if (currentPitch > 0) "+" else ""}$currentPitch ST",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Slider
        Slider(
            value = currentPitch.toFloat(),
            onValueChange = { onPitchChange(it.toInt()) },
            valueRange = Constants.MIN_PITCH_SEMITONES.toFloat()..Constants.MAX_PITCH_SEMITONES.toFloat(),
            modifier = Modifier.fillMaxWidth(),
            steps = 23 // De -12 à +12 = 25 étapes
        )

        // Aide textuelle
        Text(
            text = "Ajustez la hauteur tonale de -12 à +12 demi-tons",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
