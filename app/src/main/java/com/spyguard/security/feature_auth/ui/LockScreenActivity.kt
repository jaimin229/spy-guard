package com.spyguard.security.feature_auth.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.spyguard.security.core.security.SecurityUtils
import com.spyguard.security.feature_auth.AuthViewModel
import com.spyguard.security.ui.theme.SpyGuardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockScreenActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LOCKED_PACKAGE = "extra_locked_package"
    }

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()
        SecurityUtils.enableScreenshotProtection(this)

        val packageName = intent.getStringExtra(EXTRA_LOCKED_PACKAGE) ?: ""

        setContent {
            SpyGuardTheme {
                val uiState by viewModel.uiState.collectAsState()
                if (uiState.authType == "PATTERN") {
                    PatternLockScreen(
                        packageName = packageName,
                        viewModel = viewModel,
                        onSuccessUnlock = { finish() }
                    )
                } else {
                    PinLockScreen(
                        packageName = packageName,
                        viewModel = viewModel,
                        onSuccessUnlock = { finish() }
                    )
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        // Prevent bypassing lock screen via back button
        moveTaskToBack(true)
    }
}
