package com.spyguard.security.feature_permissions

import android.content.Context
import androidx.lifecycle.ViewModel
import com.spyguard.security.core.service.AppLockAccessibilityService
import com.spyguard.security.core.util.PermissionUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class PermissionState(
    val isAccessibilityGranted: Boolean = false,
    val isOverlayGranted: Boolean = false,
    val isUsageStatsGranted: Boolean = false
)

@HiltViewModel
class PermissionsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(PermissionState())
    val state: StateFlow<PermissionState> = _state.asStateFlow()

    fun checkPermissions(context: Context) {
        _state.update {
            PermissionState(
                isAccessibilityGranted = PermissionUtils.isAccessibilityServiceEnabled(
                    context,
                    AppLockAccessibilityService::class.java
                ),
                isOverlayGranted = PermissionUtils.canDrawOverlays(context),
                isUsageStatsGranted = PermissionUtils.hasUsageStatsPermission(context)
            )
        }
    }
}
