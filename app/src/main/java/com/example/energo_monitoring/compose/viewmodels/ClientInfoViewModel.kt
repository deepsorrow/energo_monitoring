package com.example.energo_monitoring.compose.viewmodels

import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.ContractInfo
import com.example.energo_monitoring.compose.screens.creatingNew1.ServingOrganization
import com.example.energo_monitoring.compose.screens.creatingNew3.*
import com.example.energo_monitoring.checks.data.api.ClientInfo
import com.example.energo_monitoring.checks.data.api.ProjectDescription
import com.example.energo_monitoring.checks.data.db.ResultData

class ClientInfoViewModel : ViewModel() {
    var agreementNumber: ContractInfo? by mutableStateOf(null)
    var name: String by mutableStateOf("")
    var addressUUTE: String by mutableStateOf("")
    var representativeName: String by mutableStateOf("")
    var phoneNumber: String by mutableStateOf("")
    var email: String by mutableStateOf("")
    var servingOrganization: ServingOrganization? by mutableStateOf(null)
    var hasDebt: Boolean by mutableStateOf(false)

    var commentary: String by mutableStateOf("")
    var matchesConditions: Boolean by mutableStateOf(true)

    // val devices: MutableList<AbstractDevice> = mutableListOf()
    val devices: MutableSet<AbstractDevice<*>> = HashSet()

    var deviceInQuestion: AbstractDevice<*>? by mutableStateOf(null)
    var deviceInfoInQuestion: IDeviceInfo<*>? by mutableStateOf(null)
    var deviceShouldBeAdded = false

    // var photo: Uri? by mutableStateOf(null)
    val photos = mutableStateListOf<Uri>()

    var agreementFound = false
    var modifiedByUserOnce = false

    val canAutoUpdate get() =
        !modifiedByUserOnce ||
        name == "" &&
        addressUUTE == "" &&
        representativeName == "" &&
        phoneNumber == "" &&
        email == ""

    fun autoUpdate(it: ContractInfo) {
        name = it.name
        addressUUTE = it.addressUUTE
        representativeName = it.representativeName
        phoneNumber = it.phoneNumber
        email = it.email

        modifiedByUserOnce = false
    }

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

    fun assembleData(): ResultData {
        return ResultData().also {
            //it.clientInfo = agreementNumber.value!!.assemble()
            it.clientInfo = ClientInfo().also {
                it.name = name
                it.addressUUTE = addressUUTE
                it.representativeName = representativeName
                it.phoneNumber = phoneNumber
                it.email = email
                // it.servingOrganization = servingOrganization
                it.hasDebt = hasDebt
            }

            it.project = ProjectDescription().also {
                it.photoPath = photos.firstOrNull()?.encodedPath // TODO: как?
            }

            it.deviceFlowTransducers = devices.filterIsInstance<FlowConverter>().map { it.toDataDevice() }
            it.deviceTemperatureTransducers = devices.filterIsInstance<TemperatureConverter>().map { it.toDataDevice() }
            it.devicePressureTransducers = devices.filterIsInstance<PressureConverter>().map { it.toDataDevice() }
            it.deviceCounters = devices.filterIsInstance<Counter>().map { it.toDataDevice() }
        }
    }

    fun clear() {
        agreementNumber = null
        name = ""
        addressUUTE = ""
        representativeName = ""
        phoneNumber = ""
        email = ""
        servingOrganization = null
        hasDebt = false

        commentary = ""
        matchesConditions = true

        deviceInQuestion = null
        deviceInfoInQuestion = null
        deviceShouldBeAdded = false

        agreementFound = false
        modifiedByUserOnce = false

        photos.clear()
        devices.clear()
    }
}
