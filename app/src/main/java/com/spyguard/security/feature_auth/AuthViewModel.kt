package com.spyguard.security.feature_auth

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spyguard.security.core.camera.IntruderCameraEngine
import com.spyguard.security.core.datastore.PreferencesManager
import com.spyguard.security.core.notification.SpyGuardNotificationManager
import com.spyguard.security.core.service.AppLockAccessibilityService
import com.spyguard.security.core.util.AppUtils
import com.spyguard.security.domain.repository.AppLockRepository
import com.spyguard.security.domain.repository.IntruderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val authType: String = "PIN", // "PIN" or "PATTERN"
    val isFakeCrashActive: Boolean = false,
    val isFakeLockActive: Boolean = false,
    val failedAttempts: Int = 0,
    val isUnlocked: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val appLockRepository: AppLockRepository,
    private val intruderRepository: IntruderRepository,
    private val cameraEngine: IntruderCameraEngine,
    private val notificationManager: SpyGuardNotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val authType = preferencesManager.authType.first()
            val fakeLock = preferencesManager.isFakeLockEnabled.first()
            _uiState.value = AuthUiState(
                authType = authType,
                isFakeLockActive = fakeLock
            )
        }
    }

    fun verifyPin(
        enteredPin: String,
        packageName: String,
        context: Context,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModelScope.launch {
            val savedPin = preferencesManager.getPin()
            val appName = AppUtils.getAppName(context, packageName)

            // Default fallback pin if none set yet: 1234
            val validPin = if (savedPin.isEmpty()) "1234" else savedPin

            if (enteredPin == validPin) {
                // Success
                appLockRepository.logUnlockAttempt(packageName, appName, true, _uiState.value.failedAttempts)
                AppLockAccessibilityService.unlockedAppsInSession.add(packageName)
                _uiState.value = _uiState.value.copy(isUnlocked = true, errorMessage = null)
            } else {
                // Failed
                val newFailedCount = _uiState.value.failedAttempts + 1
                val limit = preferencesManager.wrongAttemptLimit.first()
                val isIntruderSelfie = preferencesManager.isIntruderSelfieEnabled.first()
                val isFakeCrash = preferencesManager.isFakeCrashEnabled.first()

                appLockRepository.logUnlockAttempt(packageName, appName, false, newFailedCount)

                if (isIntruderSelfie && newFailedCount >= limit) {
                    val photoPath = cameraEngine.captureIntruderPhoto(lifecycleOwner)
                    if (photoPath != null) {
                        intruderRepository.logIntruder(packageName, appName, photoPath, newFailedCount)
                        notificationManager.showIntruderAlert(appName, newFailedCount)
                    }
                }

                _uiState.value = _uiState.value.copy(
                    failedAttempts = newFailedCount,
                    isFakeCrashActive = isFakeCrash && newFailedCount >= limit,
                    errorMessage = "Incorrect PIN code ($newFailedCount attempts)"
                )
            }
        }
    }

    fun verifyPattern(
        enteredPattern: String,
        packageName: String,
        context: Context,
        lifecycleOwner: LifecycleOwner
    ) {
        viewModelScope.launch {
            val savedPattern = preferencesManager.getPattern()
            val appName = AppUtils.getAppName(context, packageName)

            if (enteredPattern == savedPattern) {
                appLockRepository.logUnlockAttempt(packageName, appName, true, _uiState.value.failedAttempts)
                AppLockAccessibilityService.unlockedAppsInSession.add(packageName)
                _uiState.value = _uiState.value.copy(isUnlocked = true, errorMessage = null)
            } else {
                val newFailedCount = _uiState.value.failedAttempts + 1
                val limit = preferencesManager.wrongAttemptLimit.first()
                val isIntruderSelfie = preferencesManager.isIntruderSelfieEnabled.first()
                val isFakeCrash = preferencesManager.isFakeCrashEnabled.first()

                appLockRepository.logUnlockAttempt(packageName, appName, false, newFailedCount)

                if (isIntruderSelfie && newFailedCount >= limit) {
                    val photoPath = cameraEngine.captureIntruderPhoto(lifecycleOwner)
                    if (photoPath != null) {
                        intruderRepository.logIntruder(packageName, appName, photoPath, newFailedCount)
                        notificationManager.showIntruderAlert(appName, newFailedCount)
                    }
                }

                _uiState.value = _uiState.value.copy(
                    failedAttempts = newFailedCount,
                    isFakeCrashActive = isFakeCrash && newFailedCount >= limit,
                    errorMessage = "Incorrect Pattern ($newFailedCount attempts)"
                )
            }
        }
    }

    fun dismissFakeLock() {
        _uiState.value = _uiState.value.copy(isFakeLockActive = false)
    }
}
