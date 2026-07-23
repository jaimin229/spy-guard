package com.spyguard.security

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpyGuardApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Google Mobile Ads SDK
        MobileAds.initialize(this) {}
    }
}
