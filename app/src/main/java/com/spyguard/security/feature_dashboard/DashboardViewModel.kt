package com.spyguard.security.feature_dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spyguard.security.core.database.entity.AppLockLogEntity
import com.spyguard.security.core.database.entity.IntruderLogEntity
import com.spyguard.security.core.database.entity.LockedAppEntity
import com.spyguard.security.core.security.SecurityUtils
import com.spyguard.security.core.service.AppLockAccessibilityService
import com.spyguard.security.core.util.PermissionUtils
import com.spyguard.security.domain.repository.AppLockRepository
import com.spyguard.security.domain.repository.IntruderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val lockedAppsCount: Int = 0,
    val intruderCount: Int = 0,
    val todayAttemptsCount: Int = 0,
    val lastAttempt: AppLockLogEntity? = null,
    val isAccessibilityEnabled: Boolean = false,
    val isOverlayEnabled: Boolean = false,
    val isUsageStatsEnabled: Boolean = false,
    val isRooted: Boolean = false,
    val isDevModeEnabled: Boolean = false
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val appLockRepository: AppLockRepository,
    private val intruderRepository: IntruderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboardData(context: Context) {
        viewModelScope.launch {
            combine(
                appLockRepository.getAllLockedApps(),
                intruderRepository.getIntruderCount(),
                appLockRepository.getTodayAttemptsCount()
            ) { lockedApps, intruders, todayAttempts ->
                DashboardUiState(
                    lockedAppsCount = lockedApps.size,
                    intruderCount = intruders,
                    todayAttemptsCount = todayAttempts,
                    isAccessibilityEnabled = PermissionUtils.isAccessibilityServiceEnabled(
                        context,
                        AppLockAccessibilityService::class.java
                    ),
                    isOverlayEnabled = PermissionUtils.canDrawOverlays(context),
                    isUsageStatsEnabled = PermissionUtils.hasUsageStatsPermission(context),
                    isRooted = SecurityUtils.isRooted(context),
                    isDevModeEnabled = SecurityUtils.isDeveloperOptionsEnabled(context)
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
