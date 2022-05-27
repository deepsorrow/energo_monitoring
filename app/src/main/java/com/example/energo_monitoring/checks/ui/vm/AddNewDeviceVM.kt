package com.example.energo_monitoring.checks.ui.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class AddNewDeviceVM : ViewModel() {

    val deviceType = ObservableField("")
    val deviceName = ObservableField("")
    val deviceNumber = ObservableField("")
    val additionalField1 = ObservableField("")
    val additionalField2 = ObservableField("")

    val deviceTypes = listOf("Тепловычислитель", "Преобразователь расхода", "Преобразователь температуры", "Преобразователь давления", "Счетчик")

    fun onDeviceTypeChanged() {

    }

    fun onDeviceNameChanged() {

    }

    fun onDeviceNumberChanged() {

    }

    fun onAdditionalField1Changed() {

    }

    fun onAdditionalField2Changed() {

    }

}