package com.spyguard.security.core.database.dao

import androidx.room.*
import com.spyguard.security.core.database.entity.LockedAppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LockedAppDao {

    @Query("SELECT * FROM locked_apps ORDER BY appName ASC")
    fun getAllLockedApps(): Flow<List<LockedAppEntity>>

    @Query("SELECT * FROM locked_apps WHERE packageName = :packageName LIMIT 1")
    suspend fun getLockedApp(packageName: String): LockedAppEntity?

    @Query("SELECT packageName FROM locked_apps")
    fun getLockedPackageNames(): Flow<List<String>>

    @Query("SELECT packageName FROM locked_apps")
    suspend fun getLockedPackageNamesSync(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLockedApp(app: LockedAppEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apps: List<LockedAppEntity>)

    @Delete
    suspend fun deleteLockedApp(app: LockedAppEntity)

    @Query("DELETE FROM locked_apps WHERE packageName = :packageName")
    suspend fun deleteByPackageName(packageName: String)

    @Query("DELETE FROM locked_apps")
    suspend fun deleteAll()
}
