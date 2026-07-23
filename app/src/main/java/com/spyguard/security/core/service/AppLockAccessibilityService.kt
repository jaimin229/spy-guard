package com.spyguard.security.core.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.spyguard.security.core.database.dao.LockedAppDao
import com.spyguard.security.feature_auth.ui.LockScreenActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppLockAccessibilityService : AccessibilityService() {

    private companion object {
        private const val TAG = "AppLockAccessibility"
        // In-memory unlocked session set
        val unlockedAppsInSession = mutableSetOf<String>()
        var lastUnlockedAppTime = 0L
    }

    @Inject
    lateinit var lockedAppDao: LockedAppDao

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var cachedLockedPackages = setOf<String>()

    override fun onCreate() {
        super.onCreate()
        observeLockedApps()
    }

    private fun observeLockedApps() {
        serviceScope.launch {
            lockedAppDao.getLockedPackageNames().collect { list ->
                cachedLockedPackages = list.toSet()
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return

        // Skip self
        if (packageName == this.packageName) return

        if (cachedLockedPackages.contains(packageName)) {
            // Check if app is already unlocked in current session
            if (!unlockedAppsInSession.contains(packageName)) {
                Log.d(TAG, "Locked app detected: $packageName. Triggering authentication overlay.")
                launchLockOverlay(packageName)
            }
        }
    }

    private fun launchLockOverlay(packageName: String) {
        val intent = Intent(this, LockScreenActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra(LockScreenActivity.EXTRA_LOCKED_PACKAGE, packageName)
        }
        startActivity(intent)
    }

    override fun onInterrupt() {
        Log.w(TAG, "Accessibility Service Interrupted")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
