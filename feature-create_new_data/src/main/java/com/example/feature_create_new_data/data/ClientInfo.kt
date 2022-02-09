package com.example.feature_create_new_data.data

data class ClientInfo(
    var agreementNumber: Int,
    var name: String,
    var addressUUTE: String,
    var representativeName: String,
    var phoneNumber: String,
    var email: String,
    var boolean: Boolean,
) {
    constructor(): this(0, "", "", "", "", "", false) { }
}
