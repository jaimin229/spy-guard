package com.spyguard.security.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.spyguard.security.feature_applock.ui.AppListScreen
import com.spyguard.security.feature_applock.ui.AppLogsScreen
import com.spyguard.security.feature_dashboard.ui.DashboardScreen
import com.spyguard.security.feature_intruder.ui.IntruderGalleryScreen
import com.spyguard.security.feature_permissions.ui.PermissionGuideScreen
import com.spyguard.security.feature_settings.ui.SettingsScreen
import com.spyguard.security.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        Screen.Dashboard,
        Screen.AppList,
        Screen.AppLogs,
        Screen.IntruderGallery,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = DarkSurface,
                contentColor = TextPrimary
            ) {
                bottomNavItems.forEach { screen ->
                    val isSelected = currentRoute == screen.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            val icon = when (screen) {
                                Screen.Dashboard -> Icons.Default.Dashboard
                                Screen.AppList -> Icons.Default.Lock
                                Screen.AppLogs -> Icons.Default.History
                                Screen.IntruderGallery -> Icons.Default.CameraAlt
                                Screen.Settings -> Icons.Default.Settings
                                else -> Icons.Default.Shield
                            }
                            Icon(
                                imageVector = icon,
                                contentDescription = screen.title,
                                tint = if (isSelected) NeonGreen else TextSecondary
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                color = if (isSelected) NeonGreen else TextSecondary
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    onNavigateToPermissions = { navController.navigate(Screen.PermissionGuide.route) },
                    onNavigateToAppList = { navController.navigate(Screen.AppList.route) },
                    onNavigateToIntruders = { navController.navigate(Screen.IntruderGallery.route) }
                )
            }
            composable(Screen.AppList.route) {
                AppListScreen()
            }
            composable(Screen.AppLogs.route) {
                AppLogsScreen()
            }
            composable(Screen.IntruderGallery.route) {
                IntruderGalleryScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
            composable(Screen.PermissionGuide.route) {
                PermissionGuideScreen()
            }
        }
    }
}
