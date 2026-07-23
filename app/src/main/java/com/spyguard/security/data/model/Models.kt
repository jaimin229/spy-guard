package com.spyguard.security.data.model

data class AppInfo(
    val packageName: String,
    val appName: String,
    val isSystemApp: Boolean = false,
    val isLocked: Boolean = false,
    val isFavorite: Boolean = false
)

data class IntruderLog(
    val id: Long = 0,
    val timestamp: Long,
    val packageName: String,
    val appName: String,
    val photoPath: String,
    val failedAttempts: Int
)

data class AppLockLog(
    val id: Long = 0,
    val timestamp: Long,
    val packageName: String,
    val appName: String,
    val isSuccess: Boolean,
    val attemptCount: Int
)
