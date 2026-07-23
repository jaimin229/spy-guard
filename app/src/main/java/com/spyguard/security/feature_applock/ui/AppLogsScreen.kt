package com.spyguard.security.feature_applock.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spyguard.security.core.database.entity.AppLockLogEntity
import com.spyguard.security.core.util.DateTimeUtils
import com.spyguard.security.feature_applock.AppLockViewModel
import com.spyguard.security.ui.theme.*

@Composable
fun AppLogsScreen(
    viewModel: AppLockViewModel = hiltViewModel()
) {
    val logs by viewModel.appLockLogs.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianDark)
            .padding(16.dp)
    ) {
        Text(text = "App Lock Event Logs", style = Typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (logs.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No event logs recorded yet.", style = Typography.bodyMedium)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(logs, key = { it.id }) { log ->
                    LogItemRow(log = log)
                }
            }
        }
    }
}

@Composable
fun LogItemRow(log: AppLockLogEntity) {
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
            Column {
                Text(text = log.appName, style = Typography.bodyLarge)
                Text(
                    text = DateTimeUtils.formatDateTime(log.timestamp),
                    style = Typography.bodyMedium
                )
            }
            Text(
                text = if (log.isSuccess) "SUCCESS" else "FAILED",
                style = Typography.bodyLarge,
                color = if (log.isSuccess) NeonGreen else AlertRed
            )
        }
    }
}
