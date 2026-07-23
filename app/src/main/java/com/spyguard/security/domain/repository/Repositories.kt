package com.spyguard.security.domain.repository

import com.spyguard.security.core.database.entity.AppLockLogEntity
import com.spyguard.security.core.database.entity.IntruderLogEntity
import com.spyguard.security.core.database.entity.LockedAppEntity
import com.spyguard.security.data.model.AppInfo
import kotlinx.coroutines.flow.Flow

interface AppLockRepository {
    fun getAllLockedApps(): Flow<List<LockedAppEntity>>
    suspend fun lockApp(appInfo: AppInfo)
    suspend fun unlockApp(packageName: String)
    suspend fun isAppLocked(packageName: String): Boolean
    fun getAppLockLogs(): Flow<List<AppLockLogEntity>>
    fun getTodayAttemptsCount(): Flow<Int>
    suspend fun logUnlockAttempt(packageName: String, appName: String, isSuccess: Boolean, attemptCount: Int)
}

interface IntruderRepository {
    fun getAllIntruderLogs(): Flow<List<IntruderLogEntity>>
    fun getIntruderCount(): Flow<Int>
    suspend fun logIntruder(packageName: String, appName: String, photoPath: String, failedAttempts: Int)
    suspend fun deleteIntruderLog(log: IntruderLogEntity)
    suspend fun clearAllIntruderLogs()
}
