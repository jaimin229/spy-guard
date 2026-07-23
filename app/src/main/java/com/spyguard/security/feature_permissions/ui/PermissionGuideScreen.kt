package com.spyguard.security.feature_permissions.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spyguard.security.core.util.PermissionUtils
import com.spyguard.security.feature_permissions.PermissionsViewModel
import com.spyguard.security.ui.theme.*

@Composable
fun PermissionGuideScreen(
    viewModel: PermissionsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkPermissions(context)
    }

    var showAccessibilityDisclosure by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Required Permissions", style = Typography.headlineLarge)
        Text(
            text = "Spy Guard operates 100% offline. Enable these system permissions for offline app locking and security features.",
            style = Typography.bodyMedium
        )

        PermissionCard(
            title = "Accessibility Service",
            description = "Monitors when locked apps are opened to display security unlock screen.",
            icon = Icons.Default.Security,
            isGranted = state.isAccessibilityGranted,
            onEnableClick = { showAccessibilityDisclosure = true }
        )

        PermissionCard(
            title = "Display Over Other Apps",
            description = "Allows security PIN/Pattern overlay window over locked applications.",
            icon = Icons.Default.Layers,
            isGranted = state.isOverlayGranted,
            onEnableClick = { PermissionUtils.openOverlaySettings(context) }
        )

        PermissionCard(
            title = "Usage Access Stats",
            description = "Tracks foreground app package names for instant protection.",
            icon = Icons.Default.QueryStats,
            isGranted = state.isUsageStatsGranted,
            onEnableClick = { PermissionUtils.openUsageStatsSettings(context) }
        )
    }

    if (showAccessibilityDisclosure) {
        AlertDialog(
            onDismissRequest = { showAccessibilityDisclosure = false },
            confirmButton = {
                Button(
                    onClick = {
                        showAccessibilityDisclosure = false
                        PermissionUtils.openAccessibilitySettings(context)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen, contentColor = ObsidianDark)
                ) {
                    Text("Agree & Continue")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAccessibilityDisclosure = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            title = { Text("Accessibility Service Disclosure", style = Typography.titleLarge) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Spy Guard requires Accessibility Service permission exclusively to detect when a locked application is launched on your device.",
                        style = Typography.bodyMedium
                    )
                    Text(
                        text = "• 100% Offline: This service DOES NOT transmit any data over the internet.\n• Privacy First: This service DOES NOT read, collect, or store your messages, passwords, screen content, or personal files.",
                        style = Typography.bodyMedium,
                        color = NeonGreen
                    )
                }
            },
            containerColor = DarkCardBg
        )
    }
}

@Composable
fun PermissionCard(
    title: String,
    description: String,
    icon: ImageVector,
    isGranted: Boolean,
    onEnableClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = icon, contentDescription = null, tint = NeonGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = title, style = Typography.titleLarge)
                }
                if (isGranted) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = NeonGreen)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = Typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onEnableClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isGranted) DarkSurface else NeonGreen,
                    contentColor = if (isGranted) TextSecondary else ObsidianDark
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isGranted) "Granted & Active" else "Enable Permission")
            }
        }
    }
}
