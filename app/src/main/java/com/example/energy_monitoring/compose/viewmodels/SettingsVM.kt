package com.example.energy_monitoring.compose.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.energy_monitoring.checks.data.api.ServerApi
import com.example.energy_monitoring.checks.data.api.ServerService
import com.example.energy_monitoring.checks.ui.utils.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class SettingsVM @Inject constructor(): ViewModel() {

    var serverIp by mutableStateOf(Settings.server_ip)
    var serverPort by mutableStateOf(Settings.server_port)
    var connectionSet by mutableStateOf<Boolean?>(null)

    fun ping() = viewModelScope.launch(Dispatchers.IO) {
        ServerService.resetApi()
        connectionSet = try {
            val response = ServerService.service.ping().awaitResponse()
            response.isSuccessful && (response.body() == true)
        } catch(e: Exception) {
            false
        }
    }

    fun onChanged() {
        ServerService.resetApi()
    }
}