package com.spyguard.security.feature_applock

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spyguard.security.core.database.entity.AppLockLogEntity
import com.spyguard.security.core.util.AppUtils
import com.spyguard.security.data.model.AppInfo
import com.spyguard.security.domain.repository.AppLockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppListUiState(
    val appsList: List<AppInfo> = emptyList(),
    val filteredAppsList: List<AppInfo> = emptyList(),
    val searchQuery: String = "",
    val showSystemApps: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class AppLockViewModel @Inject constructor(
    private val appLockRepository: AppLockRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    val appLockLogs: Flow<List<AppLockLogEntity>> = appLockRepository.getAppLockLogs()

    fun loadApps(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val installedApps = AppUtils.getInstalledApps(context, includeSystemApps = true)

            appLockRepository.getAllLockedApps().collect { lockedEntities ->
                val lockedPackageSet = lockedEntities.map { it.packageName }.toSet()

                val updatedApps = installedApps.map { app ->
                    app.copy(isLocked = lockedPackageSet.contains(app.packageName))
                }

                _uiState.update { state ->
                    state.copy(
                        appsList = updatedApps,
                        isLoading = false
                    )
                }
                filterApps()
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterApps()
    }

    fun onSystemAppsToggleChanged(show: Boolean) {
        _uiState.update { it.copy(showSystemApps = show) }
        filterApps()
    }

    fun toggleAppLock(appInfo: AppInfo) {
        viewModelScope.launch {
            if (appInfo.isLocked) {
                appLockRepository.unlockApp(appInfo.packageName)
            } else {
                appLockRepository.lockApp(appInfo)
            }
        }
    }

    private fun filterApps() {
        val current = _uiState.value
        val filtered = current.appsList.filter { app ->
            val matchesSystem = if (!current.showSystemApps) !app.isSystemApp else true
            val matchesQuery = app.appName.contains(current.searchQuery, ignoreCase = true) ||
                    app.packageName.contains(current.searchQuery, ignoreCase = true)
            matchesSystem && matchesQuery
        }
        _uiState.update { it.copy(filteredAppsList = filtered) }
    }
}
