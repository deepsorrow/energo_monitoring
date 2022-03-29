package com.example.energo_monitoring.compose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.NewClientInfo

class ClientInfoViewModel: ViewModel() {
    var agreementNumber: MutableState<Int> = mutableStateOf(0)
    var name: MutableState<String> = mutableStateOf("")
    var addressUUTE: MutableState<String> = mutableStateOf("")
    var representativeName: MutableState<String> = mutableStateOf("")
    var phoneNumber: MutableState<String> = mutableStateOf("")
    var email: MutableState<String> = mutableStateOf("")
    var boolean: MutableState<Boolean> = mutableStateOf(false)

    var agreementFound: Boolean = false

    val availableClientInfos: MutableState<List<NewClientInfo>> = mutableStateOf(listOf())

    fun getAvailableClientInfos(){

    }
}