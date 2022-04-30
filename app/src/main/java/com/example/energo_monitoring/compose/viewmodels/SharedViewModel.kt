package com.example.energo_monitoring.compose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.ContractInfo

class SharedViewModel : ViewModel() {
    var clientInfo: MutableState<ContractInfo> = mutableStateOf(ContractInfo())
}