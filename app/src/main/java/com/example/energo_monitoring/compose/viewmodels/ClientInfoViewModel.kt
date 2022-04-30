package com.example.energo_monitoring.compose.viewmodels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.ContractInfo
import com.example.energo_monitoring.compose.screens.creatingNew1.ServingOrganization
import com.example.energo_monitoring.compose.screens.creatingNew3.AbstractDevice
import com.example.energo_monitoring.compose.screens.creatingNew3.IDeviceInfo

class ClientInfoViewModel : ViewModel() {
    val agreementNumber: MutableState<ContractInfo?> = mutableStateOf(null)
    val name: MutableState<String> = mutableStateOf("")
    val addressUUTE: MutableState<String> = mutableStateOf("")
    val representativeName: MutableState<String> = mutableStateOf("")
    val phoneNumber: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> = mutableStateOf("")
    val servingOrganization: MutableState<ServingOrganization?> = mutableStateOf(null)
    val boolean: MutableState<Boolean> = mutableStateOf(false)

    val commentary: MutableState<String> = mutableStateOf("")
    val matchesConditions: MutableState<Boolean> = mutableStateOf(true)

    // val devices: MutableList<AbstractDevice> = mutableListOf()
    val devices: MutableSet<AbstractDevice> = HashSet()

    var deviceInQuestion: AbstractDevice? by mutableStateOf(null)
    var deviceInfoInQuestion: IDeviceInfo<*>? by mutableStateOf(null)
    var deviceShouldBeAdded = false

    var photo: Uri? by mutableStateOf(null)

    var agreementFound: Boolean = false
    var modifiedByUserOnce = false

    val availableClientsInfo: List<ContractInfo> = listOf(
        ContractInfo(0, 123120321, "Школа №13", "Адрес школы", "А", "1", "мыло 1", false),
        ContractInfo(1, 523131321, "Дет. сад №5", "Детский сад", "Б", "2", "мыло 2", false),
        ContractInfo(2, 865120321, "Школа №153", "Школа", "В", "3", "мыло 3", false)
    )

    val availableServingOrganizations: List<ServingOrganization> = listOf(
        ServingOrganization(0, "ООО \"РСВА\"", "Евгений Сергеевич Иванов", "8(951)577-27-77"),
        ServingOrganization(1, "ООО УК \"Апельсин\"", "Сергей Александрович Иванов", "8 (38456) 349-68"),
        ServingOrganization(2, "ООО \"Таштагольская управляющая компания\"", "Шторк Игорь Александрович", "8(999)430-69-94"),
    )
}
