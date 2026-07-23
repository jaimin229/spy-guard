package com.spyguard.security.core.database.dao

import androidx.room.*
import com.spyguard.security.core.database.entity.AppLockLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppLockLogDao {

    @Query("SELECT * FROM app_lock_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<AppLockLogEntity>>

    @Query("SELECT * FROM app_lock_logs WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    fun getTodayLogs(startOfDay: Long): Flow<List<AppLockLogEntity>>

    @Query("SELECT COUNT(*) FROM app_lock_logs WHERE timestamp >= :startOfDay")
    fun getTodayAttemptsCount(startOfDay: Long): Flow<Int>

    @Query("SELECT * FROM app_lock_logs ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastLog(): AppLockLogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: AppLockLogEntity)

    @Query("DELETE FROM app_lock_logs")
    suspend fun deleteAll()
}
