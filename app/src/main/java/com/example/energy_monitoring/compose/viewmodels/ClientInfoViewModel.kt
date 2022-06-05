package com.example.energy_monitoring.compose.viewmodels

import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.energy_monitoring.compose.ContractInfo
import com.example.energy_monitoring.compose.screens.creatingNew1.ServingOrganization
import com.example.energy_monitoring.compose.screens.creatingNew3.*
import com.example.energy_monitoring.checks.data.api.ClientInfo
import com.example.energy_monitoring.checks.data.api.ProjectDescription
import com.example.energy_monitoring.checks.data.db.ResultData

class ClientInfoViewModel : ViewModel() {
    val agreementNumber: MutableState<ContractInfo?> = mutableStateOf(null)
    val name: MutableState<String> = mutableStateOf("")
    val addressUUTE: MutableState<String> = mutableStateOf("")
    val representativeName: MutableState<String> = mutableStateOf("")
    val phoneNumber: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> = mutableStateOf("")
    val servingOrganization: MutableState<ServingOrganization?> = mutableStateOf(null)
    val hasDebt: MutableState<Boolean> = mutableStateOf(false)

    val commentary: MutableState<String> = mutableStateOf("")
    val matchesConditions: MutableState<Boolean> = mutableStateOf(true)

    // val devices: MutableList<AbstractDevice> = mutableListOf()
    val devices: MutableSet<AbstractDevice<*>> = HashSet()

    var deviceInQuestion: AbstractDevice<*>? by mutableStateOf(null)
    var deviceInfoInQuestion: IDeviceInfo<*>? by mutableStateOf(null)
    var deviceShouldBeAdded = false

    // var photo: Uri? by mutableStateOf(null)
    val photos = mutableStateListOf<Uri>()

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

    fun assembleData(): ResultData {
        return ResultData().also {
            //it.clientInfo = agreementNumber.value!!.assemble()
            it.clientInfo = ClientInfo().also {
                it.name = name.value
                it.addressUUTE = addressUUTE.value
                it.representativeName = representativeName.value
                it.phoneNumber = phoneNumber.value
                it.email = email.value
                // it.servingOrganization = servingOrganization.value
                it.hasDebt = hasDebt.value
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
}
