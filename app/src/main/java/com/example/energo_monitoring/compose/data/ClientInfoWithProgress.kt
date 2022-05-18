package com.example.energo_monitoring.compose.data

import com.example.energo_monitoring.checks.data.api.ClientInfo

data class ClientInfoWithProgress(
    val clientInfo: ClientInfo,
    val progress: Int,
    val synced: SyncStatus
)