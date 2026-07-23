package com.spyguard.security.feature_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spyguard.security.core.backup.LocalBackupManager
import com.spyguard.security.core.datastore.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val localBackupManager: LocalBackupManager
) : ViewModel() {

    val isBiometricEnabled: StateFlow<Boolean> = preferencesManager.isBiometricEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val isIntruderSelfieEnabled: StateFlow<Boolean> = preferencesManager.isIntruderSelfieEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val wrongAttemptLimit: StateFlow<Int> = preferencesManager.wrongAttemptLimit
        .stateIn(viewModelScope, SharingStarted.Lazily, 3)

    val isFakeCrashEnabled: StateFlow<Boolean> = preferencesManager.isFakeCrashEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val isFakeLockEnabled: StateFlow<Boolean> = preferencesManager.isFakeLockEnabled
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun savePin(pin: String) {
        viewModelScope.launch {
            preferencesManager.savePin(pin)
        }
    }

    fun savePattern(pattern: String) {
        viewModelScope.launch {
            preferencesManager.savePattern(pattern)
        }
    }

    fun setBiometricEnabled(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setBiometricEnabled(enabled) }
    }

    fun setIntruderSelfieEnabled(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setIntruderSelfieEnabled(enabled) }
    }

    fun setWrongAttemptLimit(limit: Int) {
        viewModelScope.launch { preferencesManager.setWrongAttemptLimit(limit) }
    }

    fun setFakeCrashEnabled(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setFakeCrashEnabled(enabled) }
    }

    fun setFakeLockEnabled(enabled: Boolean) {
        viewModelScope.launch { preferencesManager.setFakeLockEnabled(enabled) }
    }

    fun createBackup(onResult: (File?) -> Unit) {
        viewModelScope.launch {
            try {
                val file = localBackupManager.createBackup()
                onResult(file)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun restoreBackup(file: File, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = localBackupManager.restoreBackup(file)
            onResult(success)
        }
    }
}
