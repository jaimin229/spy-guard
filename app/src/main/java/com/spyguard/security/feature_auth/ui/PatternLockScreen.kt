package com.spyguard.security.feature_auth.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.spyguard.security.core.util.AppUtils
import com.spyguard.security.feature_auth.AuthViewModel
import com.spyguard.security.ui.theme.*
import kotlin.math.pow
import kotlin.math.sqrt

data class PatternDot(val id: Int, val center: Offset)

@Composable
fun PatternLockScreen(
    packageName: String,
    viewModel: AuthViewModel,
    onSuccessUnlock: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()

    val appName = remember(packageName) { AppUtils.getAppName(context, packageName) }
    var selectedDots by remember { mutableStateOf<List<Int>>(emptyList()) }
    var currentTouchPosition by remember { mutableStateOf<Offset?>(null) }

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
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Draw Pattern to Unlock", style = Typography.bodyLarge, color = TextSecondary)

            uiState.errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = error, style = Typography.bodyMedium, color = AlertRed)
            }
        }

        // 3x3 Interactive Pattern Lock Canvas
        Box(
            modifier = Modifier
                .size(320.dp)
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            val dots = remember { mutableStateOf<List<PatternDot>>(emptyList()) }
            val isError = uiState.errorMessage != null

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                selectedDots = emptyList()
                                currentTouchPosition = offset
                                dots.value.find { distance(offset, it.center) < 60f }?.let { dot ->
                                    selectedDots = listOf(dot.id)
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                currentTouchPosition = change.position
                                dots.value.find { distance(change.position, it.center) < 60f }?.let { dot ->
                                    if (!selectedDots.contains(dot.id)) {
                                        selectedDots = selectedDots + dot.id
                                    }
                                }
                            },
                            onDragEnd = {
                                if (selectedDots.isNotEmpty()) {
                                    val patternString = selectedDots.joinToString("-")
                                    viewModel.verifyPattern(patternString, packageName, context, lifecycleOwner)
                                }
                                currentTouchPosition = null
                            }
                        )
                    }
            ) {
                val gridMargin = size.width / 4f
                val dotRadius = 14f
                val hitRadius = 60f

                if (dots.value.isEmpty()) {
                    val dotList = mutableListOf<PatternDot>()
                    var id = 1
                    for (row in 1..3) {
                        for (col in 1..3) {
                            dotList.add(PatternDot(id++, Offset(col * gridMargin, row * gridMargin)))
                        }
                    }
                    dots.value = dotList
                }

                val activeColor = if (isError) AlertRed else NeonGreen

                // Draw Connecting Lines between selected dots
                for (i in 0 until selectedDots.size - 1) {
                    val startDot = dots.value.first { it.id == selectedDots[i] }
                    val endDot = dots.value.first { it.id == selectedDots[i + 1] }
                    drawLine(
                        color = activeColor,
                        start = startDot.center,
                        end = endDot.center,
                        strokeWidth = 8f,
                        cap = StrokeCap.Round
                    )
                }

                // Draw Line from last selected dot to touch position
                currentTouchPosition?.let { touchPos ->
                    if (selectedDots.isNotEmpty()) {
                        val lastDot = dots.value.first { it.id == selectedDots.last() }
                        drawLine(
                            color = activeColor.copy(alpha = 0.6f),
                            start = lastDot.center,
                            end = touchPos,
                            strokeWidth = 6f,
                            cap = StrokeCap.Round
                        )
                    }
                }

                // Draw 3x3 Dots
                dots.value.forEach { dot ->
                    val isSelected = selectedDots.contains(dot.id)
                    drawCircle(
                        color = if (isSelected) activeColor else DarkSurface,
                        radius = if (isSelected) dotRadius * 1.8f else dotRadius,
                        center = dot.center
                    )
                    if (isSelected) {
                        drawCircle(
                            color = activeColor.copy(alpha = 0.3f),
                            radius = hitRadius * 0.7f,
                            center = dot.center
                        )
                    }
                }
            }
        }
    }
}

private fun distance(p1: Offset, p2: Offset): Float {
    return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))
}
