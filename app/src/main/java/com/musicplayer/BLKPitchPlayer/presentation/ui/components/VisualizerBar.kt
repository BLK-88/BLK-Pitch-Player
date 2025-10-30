package com.musicplayer.BLKPitchPlayer.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Visualiseur audio basique avec barres animées
 */
@Composable
fun AudioVisualizer(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    barCount: Int = 12
) {
    val animatedHeights = remember {
        List(barCount) { mutableStateOf(0f) }
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(100)
            animatedHeights.forEach { height ->
                height.value = if (isPlaying) Random.nextFloat() * 100 else 0f
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(barCount) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 2.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(2.dp)
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(animatedHeights[index].value / 100f)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}
