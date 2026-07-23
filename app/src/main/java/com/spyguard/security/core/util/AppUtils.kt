package com.spyguard.security.core.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.spyguard.security.data.model.AppInfo

object AppUtils {

    fun getInstalledApps(context: Context, includeSystemApps: Boolean = false): List<AppInfo> {
        val packageManager = context.packageManager
        val mainIntent = android.content.Intent(android.content.Intent.ACTION_MAIN, null).apply {
            addCategory(android.content.Intent.CATEGORY_LAUNCHER)
        }

        val launchableApps = packageManager.queryIntentActivities(mainIntent, 0)
            .map { it.activityInfo.packageName }
            .toSet()

        val installedPackages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        val resultList = mutableListOf<AppInfo>()

        for (app in installedPackages) {
            // Filter self
            if (app.packageName == context.packageName) continue

            val isSystemApp = (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0
            if (!includeSystemApps && isSystemApp) continue

            // Include app if launcher icon exists or user explicitly requested system apps
            if (launchableApps.contains(app.packageName) || isSystemApp) {
                val appName = packageManager.getApplicationLabel(app).toString()
                resultList.add(
                    AppInfo(
                        packageName = app.packageName,
                        appName = appName,
                        isSystemApp = isSystemApp,
                        isLocked = false,
                        isFavorite = false
                    )
                )
            }
        }

        return resultList.sortedBy { it.appName.lowercase() }
    }

    fun getAppName(context: Context, packageName: String): String {
        return try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (e: Exception) {
            packageName
        }
    }

    fun getAppIcon(context: Context, packageName: String): Drawable? {
        return try {
            context.packageManager.getApplicationIcon(packageName)
        } catch (e: Exception) {
            null
        }
    }

    fun getAppIconBitmap(context: Context, packageName: String): android.graphics.Bitmap? {
        return try {
            val drawable = getAppIcon(context, packageName) ?: return null
            if (drawable is android.graphics.drawable.BitmapDrawable) {
                return drawable.bitmap
            }
            val bitmap = android.graphics.Bitmap.createBitmap(
                drawable.intrinsicWidth.coerceAtLeast(1),
                drawable.intrinsicHeight.coerceAtLeast(1),
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            null
        }
    }
}
