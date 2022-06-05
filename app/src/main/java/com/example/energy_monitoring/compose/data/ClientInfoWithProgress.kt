package com.example.energy_monitoring.compose.data

import com.example.energy_monitoring.checks.data.api.ClientInfo

data class ClientInfoWithProgress(
    val clientInfo: ClientInfo,
    val progress: Int,
    val syncStatus: SyncStatus
)