package com.spyguard.security.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spyguard.security.core.database.dao.AppLockLogDao
import com.spyguard.security.core.database.dao.IntruderLogDao
import com.spyguard.security.core.database.dao.LockedAppDao
import com.spyguard.security.core.database.entity.AppLockLogEntity
import com.spyguard.security.core.database.entity.IntruderLogEntity
import com.spyguard.security.core.database.entity.LockedAppEntity

@Database(
    entities = [
        LockedAppEntity::class,
        IntruderLogEntity::class,
        AppLockLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SpyGuardDatabase : RoomDatabase() {
    abstract fun lockedAppDao(): LockedAppDao
    abstract fun intruderLogDao(): IntruderLogDao
    abstract fun appLockLogDao(): AppLockLogDao
}
