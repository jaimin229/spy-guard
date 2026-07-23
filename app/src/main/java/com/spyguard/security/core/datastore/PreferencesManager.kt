package com.spyguard.security.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.spyguard.security.core.security.KeystoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "spy_guard_preferences")

@Singleton
class PreferencesManager @Inject constructor(
    private val context: Context,
    private val keystoreManager: KeystoreManager
) {

    private companion object {
        val KEY_PIN_HASH = stringPreferencesKey("key_pin_hash")
        val KEY_PATTERN_HASH = stringPreferencesKey("key_pattern_hash")
        val KEY_AUTH_TYPE = stringPreferencesKey("key_auth_type") // "PIN" or "PATTERN"
        val KEY_BIOMETRIC_ENABLED = booleanPreferencesKey("key_biometric_enabled")
        val KEY_INTRUDER_SELFIE_ENABLED = booleanPreferencesKey("key_intruder_selfie_enabled")
        val KEY_WRONG_ATTEMPT_LIMIT = intPreferencesKey("key_wrong_attempt_limit")
        val KEY_FAKE_CRASH_ENABLED = booleanPreferencesKey("key_fake_crash_enabled")
        val KEY_FAKE_LOCK_ENABLED = booleanPreferencesKey("key_fake_lock_enabled")
        val KEY_DARK_MODE_ENABLED = booleanPreferencesKey("key_dark_mode_enabled")
        val KEY_AUTO_LOCK_DELAY = longPreferencesKey("key_auto_lock_delay")
    }

    val authType: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_AUTH_TYPE] ?: "PIN"
    }

    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_BIOMETRIC_ENABLED] ?: false
    }

    val isIntruderSelfieEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_INTRUDER_SELFIE_ENABLED] ?: true
    }

    val wrongAttemptLimit: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[KEY_WRONG_ATTEMPT_LIMIT] ?: 3
    }

    val isFakeCrashEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_FAKE_CRASH_ENABLED] ?: false
    }

    val isFakeLockEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_FAKE_LOCK_ENABLED] ?: false
    }

    val isDarkModeEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_DARK_MODE_ENABLED] ?: true
    }

    val autoLockDelay: Flow<Long> = context.dataStore.data.map { prefs ->
        prefs[KEY_AUTO_LOCK_DELAY] ?: 0L
    }

    suspend fun getPin(): String {
        var encryptedPin = ""
        context.dataStore.data.map { prefs ->
            encryptedPin = prefs[KEY_PIN_HASH] ?: ""
        }
        return keystoreManager.decrypt(encryptedPin)
    }

    suspend fun savePin(pin: String) {
        val encrypted = keystoreManager.encrypt(pin)
        context.dataStore.edit { prefs ->
            prefs[KEY_PIN_HASH] = encrypted
            prefs[KEY_AUTH_TYPE] = "PIN"
        }
    }

    suspend fun getPattern(): String {
        var encryptedPattern = ""
        context.dataStore.data.map { prefs ->
            encryptedPattern = prefs[KEY_PATTERN_HASH] ?: ""
        }
        return keystoreManager.decrypt(encryptedPattern)
    }

    suspend fun savePattern(pattern: String) {
        val encrypted = keystoreManager.encrypt(pattern)
        context.dataStore.edit { prefs ->
            prefs[KEY_PATTERN_HASH] = encrypted
            prefs[KEY_AUTH_TYPE] = "PATTERN"
        }
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[KEY_BIOMETRIC_ENABLED] = enabled }
    }

    suspend fun setIntruderSelfieEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[KEY_INTRUDER_SELFIE_ENABLED] = enabled }
    }

    suspend fun setWrongAttemptLimit(limit: Int) {
        context.dataStore.edit { prefs -> prefs[KEY_WRONG_ATTEMPT_LIMIT] = limit }
    }

    suspend fun setFakeCrashEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[KEY_FAKE_CRASH_ENABLED] = enabled }
    }

    suspend fun setFakeLockEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[KEY_FAKE_LOCK_ENABLED] = enabled }
    }

    suspend fun setDarkModeEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[KEY_DARK_MODE_ENABLED] = enabled }
    }

    suspend fun setAutoLockDelay(delayMs: Long) {
        context.dataStore.edit { prefs -> prefs[KEY_AUTO_LOCK_DELAY] = delayMs }
    }
}
