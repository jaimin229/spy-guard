package com.spyguard.security.feature_settings.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spyguard.security.feature_settings.SettingsViewModel
import com.spyguard.security.ui.theme.*

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsState()
    val isIntruderSelfieEnabled by viewModel.isIntruderSelfieEnabled.collectAsState()
    val wrongAttemptLimit by viewModel.wrongAttemptLimit.collectAsState()
    val isFakeCrashEnabled by viewModel.isFakeCrashEnabled.collectAsState()
    val isFakeLockEnabled by viewModel.isFakeLockEnabled.collectAsState()

    var showPinDialog by remember { mutableStateOf(false) }
    var newPinText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Security Settings", style = Typography.headlineLarge)

        // Security Credentials Section
        SettingsHeader(title = "Authentication Credentials")

        SettingsTile(
            title = "Change PIN Code",
            subtitle = "Set 4-digit Master Security PIN",
            icon = Icons.Default.Lock,
            onClick = { showPinDialog = true }
        )

        SettingsSwitchTile(
            title = "Biometric / Fingerprint Unlock",
            subtitle = "Allow device fingerprint sensor for authentication",
            icon = Icons.Default.Fingerprint,
            checked = isBiometricEnabled,
            onCheckedChange = { viewModel.setBiometricEnabled(it) }
        )

        // Intruder & Camera Protection
        SettingsHeader(title = "Intruder Selfie Protection")

        SettingsSwitchTile(
            title = "Silent Intruder Selfie",
            subtitle = "Capture front-camera photo on failed attempts",
            icon = Icons.Default.CameraAlt,
            checked = isIntruderSelfieEnabled,
            onCheckedChange = { viewModel.setIntruderSelfieEnabled(it) }
        )

        // Fake Screen Options
        SettingsHeader(title = "Disguise & Decoy Screens")

        SettingsSwitchTile(
            title = "Fake Crash Dialog",
            subtitle = "Display system error dialog on wrong PIN",
            icon = Icons.Default.Warning,
            checked = isFakeCrashEnabled,
            onCheckedChange = { viewModel.setFakeCrashEnabled(it) }
        )

        // Offline Backup & Restore
        SettingsHeader(title = "Offline Local Backup")

        SettingsTile(
            title = "Export Local Encrypted Backup",
            subtitle = "Save encrypted security configuration file",
            icon = Icons.Default.Backup,
            onClick = {
                viewModel.createBackup { file ->
                    if (file != null) {
                        Toast.makeText(context, "Backup created: ${file.name}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Backup creation failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

    if (showPinDialog) {
        AlertDialog(
            onDismissRequest = { showPinDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (newPinText.length >= 4) {
                        viewModel.savePin(newPinText)
                        showPinDialog = false
                        Toast.makeText(context, "PIN Saved Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "PIN must be at least 4 digits", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Save PIN", color = NeonGreen)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPinDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            title = { Text("Set Master Security PIN") },
            text = {
                OutlinedTextField(
                    value = newPinText,
                    onValueChange = { if (it.length <= 6) newPinText = it },
                    label = { Text("Enter 4-6 Digit PIN") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonGreen,
                        unfocusedBorderColor = BorderColor
                    )
                )
            },
            containerColor = DarkCardBg
        )
    }
}

@Composable
fun SettingsHeader(title: String) {
    Text(
        text = title,
        style = Typography.titleLarge,
        color = NeonGreen,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun SettingsTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = NeonGreen)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = Typography.bodyLarge)
                Text(text = subtitle, style = Typography.bodyMedium)
            }
        }
    }
}

@Composable
fun SettingsSwitchTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
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
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = NeonGreen)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = title, style = Typography.bodyLarge)
                    Text(text = subtitle, style = Typography.bodyMedium)
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = NeonGreen)
            )
        }
    }
}
