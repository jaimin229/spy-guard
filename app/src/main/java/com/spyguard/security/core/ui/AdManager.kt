package com.spyguard.security.core.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdManager {

    private const val TAG = "AdManager"

    // Test Ad Unit IDs (Google Sample Test IDs)
    private const val TEST_APP_OPEN_AD_ID = "ca-app-pub-3940256099942544/9257395921"
    private const val TEST_INTERSTITIAL_AD_ID = "ca-app-pub-3940256099942544/1033173712"

    private var mAppOpenAd: AppOpenAd? = null
    private var mInterstitialAd: InterstitialAd? = null
    private var isShowingAd = false

    // --- App Open Ads ---
    fun loadAppOpenAd(context: Context) {
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            TEST_APP_OPEN_AD_ID,
            request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    Log.d(TAG, "App Open Ad Loaded")
                    mAppOpenAd = ad
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e(TAG, "App Open Ad Failed to Load: ${loadAdError.message}")
                    mAppOpenAd = null
                }
            }
        )
    }

    fun showAppOpenAd(activity: Activity) {
        if (!isShowingAd && mAppOpenAd != null) {
            isShowingAd = true
            mAppOpenAd?.show(activity)
            mAppOpenAd = null
            isShowingAd = false
            loadAppOpenAd(activity) // Preload next
        } else {
            loadAppOpenAd(activity)
        }
    }

    // --- Interstitial Ads ---
    fun loadInterstitialAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            TEST_INTERSTITIAL_AD_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Interstitial Ad Loaded")
                    mInterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e(TAG, "Interstitial Ad Failed to Load: ${loadAdError.message}")
                    mInterstitialAd = null
                }
            }
        )
    }

    fun showInterstitialAd(activity: Activity, onAdDismissed: () -> Unit = {}) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(activity)
            mInterstitialAd = null
            onAdDismissed()
            loadInterstitialAd(activity) // Preload next
        } else {
            onAdDismissed()
            loadInterstitialAd(activity)
        }
    }
}
