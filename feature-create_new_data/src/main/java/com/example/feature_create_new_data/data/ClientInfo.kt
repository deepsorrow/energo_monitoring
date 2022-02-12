package com.example.feature_create_new_data.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClientInfo(
    @PrimaryKey
    var id: Int,
    var agreementNumber: Int,
    var name: String,
    var addressUUTE: String,
    var representativeName: String,
    var phoneNumber: String,
    var email: String,
    var boolean: Boolean,
) {
    constructor(): this(0, 0, "", "", "", "", "", false) { }
}
