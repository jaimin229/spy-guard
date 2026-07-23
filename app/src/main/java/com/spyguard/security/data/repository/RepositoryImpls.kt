package com.spyguard.security.data.repository

import com.spyguard.security.core.database.dao.AppLockLogDao
import com.spyguard.security.core.database.dao.IntruderLogDao
import com.spyguard.security.core.database.dao.LockedAppDao
import com.spyguard.security.core.database.entity.AppLockLogEntity
import com.spyguard.security.core.database.entity.IntruderLogEntity
import com.spyguard.security.core.database.entity.LockedAppEntity
import com.spyguard.security.data.model.AppInfo
import com.spyguard.security.domain.repository.AppLockRepository
import com.spyguard.security.domain.repository.IntruderRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLockRepositoryImpl @Inject constructor(
    private val lockedAppDao: LockedAppDao,
    private val appLockLogDao: AppLockLogDao
) : AppLockRepository {

    override fun getAllLockedApps(): Flow<List<LockedAppEntity>> = lockedAppDao.getAllLockedApps()

    override suspend fun lockApp(appInfo: AppInfo) {
        lockedAppDao.insertLockedApp(
            LockedAppEntity(
                packageName = appInfo.packageName,
                appName = appInfo.appName,
                isSystemApp = appInfo.isSystemApp
            )
        )
    }

    override suspend fun unlockApp(packageName: String) {
        lockedAppDao.deleteByPackageName(packageName)
    }

    override suspend fun isAppLocked(packageName: String): Boolean {
        return lockedAppDao.getLockedApp(packageName) != null
    }

    override fun getAppLockLogs(): Flow<List<AppLockLogEntity>> = appLockLogDao.getAllLogs()

    override fun getTodayAttemptsCount(): Flow<Int> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return appLockLogDao.getTodayAttemptsCount(calendar.timeInMillis)
    }

    override suspend fun logUnlockAttempt(
        packageName: String,
        appName: String,
        isSuccess: Boolean,
        attemptCount: Int
    ) {
        appLockLogDao.insertLog(
            AppLockLogEntity(
                timestamp = System.currentTimeMillis(),
                packageName = packageName,
                appName = appName,
                isSuccess = isSuccess,
                attemptCount = attemptCount
            )
        )
    }
}

@Singleton
class IntruderRepositoryImpl @Inject constructor(
    private val intruderLogDao: IntruderLogDao
) : IntruderRepository {

    override fun getAllIntruderLogs(): Flow<List<IntruderLogEntity>> = intruderLogDao.getAllIntruderLogs()

    override fun getIntruderCount(): Flow<Int> = intruderLogDao.getIntruderCount()

    override suspend fun logIntruder(
        packageName: String,
        appName: String,
        photoPath: String,
        failedAttempts: Int
    ) {
        intruderLogDao.insertIntruderLog(
            IntruderLogEntity(
                timestamp = System.currentTimeMillis(),
                packageName = packageName,
                appName = appName,
                photoPath = photoPath,
                failedAttempts = failedAttempts
            )
        )
    }

    override suspend fun deleteIntruderLog(log: IntruderLogEntity) {
        intruderLogDao.deleteIntruderLog(log)
    }

    override suspend fun clearAllIntruderLogs() {
        intruderLogDao.deleteAll()
    }
}
