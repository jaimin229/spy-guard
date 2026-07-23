package com.spyguard.security.feature_intruder.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spyguard.security.core.database.entity.IntruderLogEntity
import com.spyguard.security.core.util.DateTimeUtils
import com.spyguard.security.feature_intruder.IntruderViewModel
import com.spyguard.security.ui.theme.*
import java.io.File

@Composable
fun IntruderGalleryScreen(
    viewModel: IntruderViewModel = hiltViewModel()
) {
    val intruderLogs by viewModel.intruderLogs.collectAsState(initial = emptyList())
    var selectedLog by remember { mutableStateOf<IntruderLogEntity?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Intruder Selfies", style = Typography.headlineLarge)
            if (intruderLogs.isNotEmpty()) {
                IconButton(onClick = { viewModel.clearAllIntruderLogs() }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear All", tint = AlertRed)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (intruderLogs.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "No Intruders Detected Yet", style = Typography.titleLarge, color = TextSecondary)
                    Text(text = "Selfies are automatically captured on wrong PIN/Pattern attempts", style = Typography.bodyMedium)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(intruderLogs, key = { it.id }) { log ->
                    IntruderGridCard(log = log, onClick = { selectedLog = log })
                }
            }
        }
    }

    selectedLog?.let { log ->
        IntruderDetailDialog(
            log = log,
            onDismiss = { selectedLog = null },
            onDelete = {
                viewModel.deleteIntruderLog(log)
                selectedLog = null
            }
        )
    }
}

@Composable
fun IntruderGridCard(
    log: IntruderLogEntity,
    onClick: () -> Unit
) {
    val photoFile = remember(log.photoPath) { File(log.photoPath) }
    val bitmap = remember(photoFile) {
        if (photoFile.exists()) BitmapFactory.decodeFile(photoFile.absolutePath) else null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Intruder Selfie",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(DarkSurface), contentAlignment = Alignment.Center) {
                        Text(text = "No Photo", style = Typography.bodyMedium)
                    }
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = log.appName, style = Typography.bodyLarge, maxLines = 1)
                Text(text = DateTimeUtils.formatDateTime(log.timestamp), style = Typography.bodyMedium, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun IntruderDetailDialog(
    log: IntruderLogEntity,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    val photoFile = remember(log.photoPath) { File(log.photoPath) }
    val bitmap = remember(photoFile) {
        if (photoFile.exists()) BitmapFactory.decodeFile(photoFile.absolutePath) else null
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text("Delete Record", color = AlertRed)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = TextSecondary)
            }
        },
        title = { Text(text = "Intruder Details - ${log.appName}") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(260.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Date/Time: ${DateTimeUtils.formatDateTime(log.timestamp)}", style = Typography.bodyMedium)
                Text(text = "Locked App: ${log.appName}", style = Typography.bodyMedium)
                Text(text = "Failed Attempts: ${log.failedAttempts}", style = Typography.bodyMedium, color = AlertRed)
            }
        },
        containerColor = DarkCardBg
    )
}
