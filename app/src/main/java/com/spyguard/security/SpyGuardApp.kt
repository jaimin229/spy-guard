package com.spyguard.security

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpyGuardApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
