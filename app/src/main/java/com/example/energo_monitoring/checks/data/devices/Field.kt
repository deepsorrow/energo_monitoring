package com.example.energo_monitoring.checks.data.devices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Field(
    val value: String,
    val correctValue: String
) : Parcelable {
    constructor(value: String) : this(value, value)


}