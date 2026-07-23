package com.spyguard.security.feature_dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spyguard.security.feature_dashboard.DashboardViewModel
import com.spyguard.security.ui.theme.*

@Composable
fun DashboardScreen(
    onNavigateToPermissions: () -> Unit,
    onNavigateToAppList: () -> Unit,
    onNavigateToIntruders: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDashboardData(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Security Dashboard",
            style = Typography.headlineLarge,
            color = TextPrimary
        )

        // Security Status Banner
        val isFullyProtected = uiState.isAccessibilityEnabled && uiState.isOverlayEnabled
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isFullyProtected) DarkCardBg else Color(0xFF331418)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isFullyProtected) Icons.Default.Shield else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (isFullyProtected) NeonGreen else AlertRed,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = if (isFullyProtected) "Device Protected 100%" else "Action Required",
                        style = Typography.titleLarge,
                        color = if (isFullyProtected) NeonGreen else AlertRed
                    )
                    Text(
                        text = if (isFullyProtected) "Offline Security Active" else "Enable required permissions to protect apps",
                        style = Typography.bodyMedium
                    )
                }
            }
        }

        // Stats Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Protected Apps",
                value = "${uiState.lockedAppsCount}",
                icon = Icons.Default.Lock,
                accentColor = NeonGreen,
                onClick = onNavigateToAppList
            )
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Intruders",
                value = "${uiState.intruderCount}",
                icon = Icons.Default.CameraAlt,
                accentColor = CyberCyan,
                onClick = onNavigateToIntruders
            )
        }

        // Today's Attempts
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkCardBg),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Today's Unlock Attempts", style = Typography.bodyLarge)
                }
                Text(
                    text = "${uiState.todayAttemptsCount}",
                    style = Typography.titleLarge,
                    color = NeonGreen
                )
            }
        }

        // System Diagnostic Warnings
        Text(text = "Security Diagnostics", style = Typography.titleLarge)

        DiagnosticItem(
            title = "Accessibility Protection Service",
            subtitle = if (uiState.isAccessibilityEnabled) "Active" else "Disabled",
            isOk = uiState.isAccessibilityEnabled,
            onClick = onNavigateToPermissions
        )

        DiagnosticItem(
            title = "Display Over Apps Permission",
            subtitle = if (uiState.isOverlayEnabled) "Active" else "Disabled",
            isOk = uiState.isOverlayEnabled,
            onClick = onNavigateToPermissions
        )

        DiagnosticItem(
            title = "Root Access Warning",
            subtitle = if (uiState.isRooted) "Root Binary Detected" else "Device Secure (Not Rooted)",
            isOk = !uiState.isRooted,
            onClick = {}
        )

        DiagnosticItem(
            title = "Developer Mode Warning",
            subtitle = if (uiState.isDevModeEnabled) "Developer Options Enabled" else "Developer Mode Disabled",
            isOk = !uiState.isDevModeEnabled,
            onClick = {}
        )

        // AdMob Banner Ad
        com.spyguard.security.core.ui.BannerAdView()
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = title, style = Typography.bodyMedium)
        }
    }
}

@Composable
fun DiagnosticItem(
    title: String,
    subtitle: String,
    isOk: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isOk) Icons.Default.CheckCircle else Icons.Default.Warning,
                contentDescription = null,
                tint = if (isOk) NeonGreen else AlertRed
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = Typography.bodyLarge)
                Text(text = subtitle, style = Typography.bodyMedium, color = if (isOk) TextSecondary else AlertRed)
            }
        }
    }
}
