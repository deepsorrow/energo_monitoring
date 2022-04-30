package com.example.energo_monitoring.compose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.ContractInfo
import com.example.energo_monitoring.compose.screens.creatingNew1.ServingOrganization

class ClientInfoViewModel : ViewModel() {
    var agreementNumber: MutableState<ContractInfo?> = mutableStateOf(null)
    var name: MutableState<String> = mutableStateOf("")
    var addressUUTE: MutableState<String> = mutableStateOf("")
    var representativeName: MutableState<String> = mutableStateOf("")
    var phoneNumber: MutableState<String> = mutableStateOf("")
    var email: MutableState<String> = mutableStateOf("")
    var servingOrganization: MutableState<ServingOrganization?> = mutableStateOf(null)
    var boolean: MutableState<Boolean> = mutableStateOf(false)

    var agreementFound: Boolean = false

    var modifiedByUserOnce = false

    val availableClientInfos: List<ContractInfo> = listOf(
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
