package com.musicplayer.BLKPitchPlayer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.musicplayer.BLKPitchPlayer.util.PermissionHelper

/**
 * ViewModel pour gérer l'état des permissions
 */
@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val permissionHelper: PermissionHelper
) : ViewModel() {

    private val _permissionState = MutableStateFlow<PermissionState>(
        PermissionState.Initial
    )
    val permissionState: StateFlow<PermissionState> = _permissionState.asStateFlow()

    init {
        checkPermissions()
    }

    /**
     * Vérifie les permissions actuelles
     */
    fun checkPermissions() {
        viewModelScope.launch {
            if (permissionHelper.hasAudioPermissions()) {
                _permissionState.value = PermissionState.Granted
            } else {
                _permissionState.value = PermissionState.NotGranted
            }
        }
    }

    /**
     * Marque les permissions comme demandées
     */
    fun onPermissionsRequested() {
        _permissionState.value = PermissionState.PermissionRequested
    }
}

/**
 * États possibles des permissions
 */
sealed class PermissionState {
    object Initial : PermissionState()
    object PermissionRequested : PermissionState()
    object Granted : PermissionState()
    object NotGranted : PermissionState()
}
