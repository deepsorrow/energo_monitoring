package com.example.energo_monitoring.checks.data.devices

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Field<T : Parcelable>(
    val value: T,
    val correctValue: T
) : Parcelable {
    constructor(value: T) : this(value, value)
}