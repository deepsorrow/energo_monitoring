package com.example.energy_monitoring.compose.data

data class DestinationChangedCallback(
    val navigationId: Int,
    val saveDataCallback: () -> Unit
)
