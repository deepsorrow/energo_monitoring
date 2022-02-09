package com.example.feature_create_new_data.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.feature_create_new_data.data.ClientInfo

class SharedViewModel : ViewModel() {
    var clientInfo: MutableState<ClientInfo> = mutableStateOf(ClientInfo())
}