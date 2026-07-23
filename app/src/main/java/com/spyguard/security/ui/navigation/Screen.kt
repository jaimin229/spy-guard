package com.spyguard.security.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Dashboard : Screen("dashboard", "Dashboard")
    object AppList : Screen("app_list", "App Lock")
    object AppLogs : Screen("app_logs", "Logs")
    object IntruderGallery : Screen("intruder_gallery", "Intruders")
    object Settings : Screen("settings", "Settings")
    object PermissionGuide : Screen("permission_guide", "Permissions")
}
