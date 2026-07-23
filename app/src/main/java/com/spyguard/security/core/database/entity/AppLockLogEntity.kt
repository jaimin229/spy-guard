package com.spyguard.security.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_lock_logs")
data class AppLockLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val packageName: String,
    val appName: String,
    val isSuccess: Boolean,
    val attemptCount: Int
)
