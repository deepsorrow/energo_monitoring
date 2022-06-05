package com.example.energy_monitoring.checks.data.devices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Field(
    var value: String, // значение в процессе проверки
    var initialValue: String // значение на этапе создания акта
) : Parcelable {
    constructor(value: String) : this(value, value)

    @JvmName("getValue1")
    fun getValue() = value.ifEmpty { initialValue }
}