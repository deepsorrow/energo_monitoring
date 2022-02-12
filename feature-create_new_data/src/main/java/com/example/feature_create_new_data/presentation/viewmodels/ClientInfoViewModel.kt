package com.example.feature_create_new_data.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.feature_create_new_data.data.ClientInfo

class ClientInfoViewModel: ViewModel() {
    var agreementNumber: MutableState<Int> = mutableStateOf(0)
    var name: MutableState<String> = mutableStateOf("")
    var addressUUTE: MutableState<String> = mutableStateOf("")
    var representativeName: MutableState<String> = mutableStateOf("")
    var phoneNumber: MutableState<String> = mutableStateOf("")
    var email: MutableState<String> = mutableStateOf("")
    var boolean: MutableState<Boolean> = mutableStateOf(false)

    var agreementFound: Boolean = false

    val availableClientInfos: MutableState<List<ClientInfo>> = mutableStateOf(listOf())

    fun getAvailableClientInfos(){

    }
}