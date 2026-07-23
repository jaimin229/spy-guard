package com.spyguard.security.feature_auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spyguard.security.core.util.AppUtils
import com.spyguard.security.feature_auth.AuthViewModel
import com.spyguard.security.ui.theme.*

@Composable
fun PinLockScreen(
    packageName: String,
    viewModel: AuthViewModel,
    onSuccessUnlock: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()

    var enteredPin by remember { mutableStateOf("") }
    val appName = remember(packageName) { AppUtils.getAppName(context, packageName) }

    LaunchedEffect(uiState.isUnlocked) {
        if (uiState.isUnlocked) {
            onSuccessUnlock()
        }
    }

    if (uiState.isFakeCrashActive) {
        FakeCrashScreen(onCloseApp = onSuccessUnlock)
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = NeonGreen,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Protected Application", style = Typography.bodyMedium, color = TextSecondary)
            Text(text = appName, style = Typography.headlineMedium, color = TextPrimary)

            Spacer(modifier = Modifier.height(24.dp))

            // PIN dots
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = if (index < enteredPin.length) NeonGreen else DarkSurface,
                                shape = CircleShape
                            )
                    )
                }
            }

            uiState.errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = error, style = Typography.bodyMedium, color = AlertRed)
            }
        }

        // Numeric Keypad Grid
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            val keypad = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("", "0", "DEL")
            )

            keypad.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    row.forEach { digit ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (digit.isNotEmpty()) {
                                Button(
                                    onClick = {
                                        if (digit == "DEL") {
                                            if (enteredPin.isNotEmpty()) enteredPin = enteredPin.dropLast(1)
                                        } else {
                                            if (enteredPin.length < 4) {
                                                enteredPin += digit
                                                if (enteredPin.length == 4) {
                                                    viewModel.verifyPin(enteredPin, packageName, context, lifecycleOwner)
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier.size(72.dp),
                                    shape = CircleShape,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DarkCardBg,
                                        contentColor = TextPrimary
                                    )
                                ) {
                                    if (digit == "DEL") {
                                        Icon(imageVector = Icons.Default.Backspace, contentDescription = "Delete", tint = TextPrimary)
                                    } else {
                                        Text(text = digit, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
