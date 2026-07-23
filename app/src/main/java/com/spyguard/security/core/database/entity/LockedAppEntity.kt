package com.spyguard.security.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locked_apps")
data class LockedAppEntity(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val isSystemApp: Boolean = false,
    val isFavorite: Boolean = false,
    val lockedAt: Long = System.currentTimeMillis()
)
