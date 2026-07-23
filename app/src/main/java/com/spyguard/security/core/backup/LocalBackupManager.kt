package com.spyguard.security.core.backup

import android.content.Context
import com.google.gson.Gson
import com.spyguard.security.core.database.dao.IntruderLogDao
import com.spyguard.security.core.database.dao.LockedAppDao
import com.spyguard.security.core.database.entity.IntruderLogEntity
import com.spyguard.security.core.database.entity.LockedAppEntity
import com.spyguard.security.core.security.KeystoreManager
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

data class BackupPayload(
    val version: Int = 1,
    val timestamp: Long = System.currentTimeMillis(),
    val lockedApps: List<LockedAppEntity>,
    val intruderLogs: List<IntruderLogEntity>
)

@Singleton
class LocalBackupManager @Inject constructor(
    private val context: Context,
    private val lockedAppDao: LockedAppDao,
    private val intruderLogDao: IntruderLogDao,
    private val keystoreManager: KeystoreManager,
    private val gson: Gson
) {

    suspend fun createBackup(): File {
        val lockedApps = lockedAppDao.getAllLockedApps().first()
        val intruderLogs = intruderLogDao.getAllIntruderLogs().first()

        val payload = BackupPayload(
            lockedApps = lockedApps,
            intruderLogs = intruderLogs
        )

        val jsonString = gson.toJson(payload)
        val encryptedData = keystoreManager.encrypt(jsonString)

        val backupDir = File(context.filesDir, "backups").apply { if (!exists()) mkdirs() }
        val backupFile = File(backupDir, "spy_guard_backup_${System.currentTimeMillis()}.sgb")
        backupFile.writeText(encryptedData)

        return backupFile
    }

    suspend fun restoreBackup(backupFile: File): Boolean {
        return try {
            val encryptedText = backupFile.readText()
            val decryptedJson = keystoreManager.decrypt(encryptedText)
            if (decryptedJson.isEmpty()) return false

            val payload = gson.fromJson(decryptedJson, BackupPayload::class.java)

            // Restore locked apps
            lockedAppDao.insertAll(payload.lockedApps)

            // Restore intruder logs
            for (log in payload.intruderLogs) {
                intruderLogDao.insertIntruderLog(log)
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
