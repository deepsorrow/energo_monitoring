package com.example.energo_monitoring.compose

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.energo_monitoring.compose.screens.creatingNew1.IListEntry

@Entity
data class ContractInfo(
    @PrimaryKey
    var id: Int,
    var agreementNumber: Int,
    var name: String,
    var addressUUTE: String,
    var representativeName: String,
    var phoneNumber: String,
    var email: String,
    var boolean: Boolean,
) : IListEntry {
    constructor(): this(0, 0, "", "", "", "", "", false) { }

    override val listLabel: String
        get() = "${agreementNumber}\n${name}"
}
