package com.spyguard.security.core.database.dao

import androidx.room.*
import com.spyguard.security.core.database.entity.IntruderLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IntruderLogDao {

    @Query("SELECT * FROM intruder_logs ORDER BY timestamp DESC")
    fun getAllIntruderLogs(): Flow<List<IntruderLogEntity>>

    @Query("SELECT COUNT(*) FROM intruder_logs")
    fun getIntruderCount(): Flow<Int>

    @Query("SELECT * FROM intruder_logs ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastIntruderLog(): IntruderLogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntruderLog(log: IntruderLogEntity)

    @Delete
    suspend fun deleteIntruderLog(log: IntruderLogEntity)

    @Query("DELETE FROM intruder_logs")
    suspend fun deleteAll()
}
