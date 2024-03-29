package com.example.energy_monitoring.compose

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.energy_monitoring.compose.screens.creatingNew1.IListEntry
import com.example.energy_monitoring.checks.data.api.ClientInfo
import kotlin.random.Random
import kotlin.random.nextInt

@Entity
data class ContractInfo(
    @PrimaryKey
    var id: Int,
    var agreementNumber: String,
    var name: String,
    var addressUUTE: String,
    var representativeName: String,
    var phoneNumber: String,
    var email: String,
    var hasDebt: Boolean,
) : IListEntry {
    constructor(): this(0, "0", "", "", "", "", "", false)

    override val listLabel: String
        get() = "${agreementNumber}\n${name}"

    fun assemble(): ClientInfo {
        return ClientInfo().also {
            it.id = Random.nextInt(100000)
            it.agreementNumber = agreementNumber
            it.addressUUTE = addressUUTE
            it.name = name
            it.representativeName = representativeName
            it.phoneNumber = phoneNumber
            it.email = email
            // it.hasDebt = hasDebt
        }
    }
}
