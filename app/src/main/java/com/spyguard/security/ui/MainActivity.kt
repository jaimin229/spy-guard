package com.spyguard.security.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.spyguard.security.core.security.SecurityUtils
import com.spyguard.security.core.service.OverlayLockService
import com.spyguard.security.ui.navigation.MainNavGraph
import com.spyguard.security.ui.theme.SpyGuardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()
        SecurityUtils.enableScreenshotProtection(this)

        // Start background security service
        val serviceIntent = Intent(this, OverlayLockService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        // Preload Ads for maximum revenue
        com.spyguard.security.core.ui.AdManager.loadInterstitialAd(this)
        com.spyguard.security.core.ui.AdManager.loadRewardedAd(this)

        setContent {
            SpyGuardTheme {
                MainNavGraph()
            }
        }
    }
}
