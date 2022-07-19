package com.example.energy_monitoring.compose.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.energy_monitoring.compose.ContractInfo
import com.example.energy_monitoring.compose.screens.creatingNew1.ServingOrganization
import com.example.energy_monitoring.compose.screens.creatingNew3.*
import com.example.energy_monitoring.checks.data.api.ClientInfo
import com.example.energy_monitoring.checks.data.api.OrganizationInfo
import com.example.energy_monitoring.checks.data.api.ProjectDescription
import com.example.energy_monitoring.checks.data.db.OtherInfo
import com.example.energy_monitoring.checks.data.db.ResultData
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energy_monitoring.checks.data.files.ProjectFile
import com.example.energy_monitoring.checks.domain.repo.ResultDataRepository
import com.example.energy_monitoring.checks.ui.utils.SharedPreferencesManager
import com.example.energy_monitoring.checks.ui.utils.Utils
import com.example.energy_monitoring.compose.domain.DropDownMenu
import com.example.energy_monitoring.compose.screens.creatingNew2.DropDownProjectActions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.min
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class ClientInfoViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak")
    @ApplicationContext val context: Context,
    val repository: ResultDataRepository
) : ViewModel() {
    var dataId: Int = Random.nextInt(10000, 100000)
    var agreementNumber by mutableStateOf("")
    var name by mutableStateOf("")
    var addressUUTE by mutableStateOf("")
    var representativeName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")
    var servingOrganization by mutableStateOf(ServingOrganization())
    var hasDebt by mutableStateOf(false)

    var commentaryFiles by mutableStateOf("")
    var matchesConditions by mutableStateOf(true)

    var devices = mutableStateListOf<AbstractDevice<*>>()
    var projectDropDownItems = DropDownProjectActions.values().map { DropDownMenu(it.title, it.icon) }

    var deviceInQuestion: AbstractDevice<*>? by mutableStateOf(null)
    var deviceInfoInQuestion: IDeviceInfo<*>? by mutableStateOf(null)
    var deviceShouldBeAdded = false

    // var photo: Uri? by mutableStateOf(null)
    var projectFiles = mutableStateListOf<ProjectFile>()
    var removeImageIndex by mutableStateOf<Int?>(null)
    var showFilePreview by mutableStateOf(false)
    var filePreview by mutableStateOf(ProjectFile())

    var agreementFound: Boolean = false

    val availableClientsInfo: List<ContractInfo> = listOf(
        ContractInfo(0, "ЭМС-90/21_ТО", "МБ ДОУ \"ДС № 150\"", "пр. Октябрьский, 46а", "Наталья Павловна", "8(384)377-93-87", "det.sad150@yandex.ru", false),
        ContractInfo(1, "ЭМС-91/21_ТО", "МБ ДОУ \"ДС № 237\"", "пр. Кузнецкстроевский, 32", "Лариса Ильинична", "8(913)070-99-97", "dou237@mail.ru", false),
        ContractInfo(2, "ЭМС-92-21_ТО", "МБ ДОУ \"ДС № 157\"", "ул. 40 лет ВЛКСМ, 78а", "Марина Федоровна", "8(384)354-80-36", "DOU157@yandex.ru", false)
    )

    val availableServingOrganizations: List<ServingOrganization> = listOf(
        ServingOrganization(0, "ООО \"РСВА\"", "Евгений Сергеевич Иванов", "8(951)577-27-77"),
        ServingOrganization(
            1,
            "ООО УК \"Апельсин\"",
            "Сергей Александрович Иванов",
            "8 (38456) 349-68"
        ),
        ServingOrganization(
            2,
            "ООО \"Таштагольская управляющая компания\"",
            "Шторк Игорь Александрович",
            "8(999)430-69-94"
        ),
    )

    private fun assembleData(context: Context) = ResultData().also { data ->
        data.clientInfo = ClientInfo().also {
            it.id = Random.nextInt(1, 100000)
            it.dataId = dataId
            it.agreementNumber = agreementNumber
            it.name = name
            it.addressUUTE = addressUUTE
            it.representativeName = representativeName
            it.phoneNumber = phoneNumber
            it.email = email
            // it.servingOrganization = servingOrganization.value
            it.hasDebt = hasDebt
        }

        data.project = ProjectDescription().also {
            it.id = Random.nextInt(1, 100000)
            it.dataId = dataId
            it.files = projectFiles
        }

        data.organizationInfo = OrganizationInfo().also {
            it.id = Random.nextInt(1, 100000)
            it.dataId = dataId
        }

        data.otherInfo = OtherInfo().also {
            it.dataId = dataId
            it.projectId = data.project?.id ?: 0
            it.clientId = data.clientInfo?.id ?: 0
            it.userId = SharedPreferencesManager.getUserId(context)
        }

        data.deviceTemperatureCounters = devices.filterIsInstance<HeatCalculator>().map { it.toDataDevice(dataId) }
        data.deviceFlowTransducers = devices.filterIsInstance<FlowConverter>().map { it.toDataDevice(dataId) }
        data.deviceTemperatureTransducers = devices.filterIsInstance<TemperatureConverter>().map { it.toDataDevice(dataId) }
        data.devicePressureTransducers = devices.filterIsInstance<PressureConverter>().map { it.toDataDevice(dataId) }
        data.deviceCounters = devices.filterIsInstance<Counter>().map { it.toDataDevice(dataId) }
    }

    fun saveDataToRepository(context: Context) {
        val data = assembleData(context)

        // 1 - ClientInfo
        data.clientInfo?.let { repository.insertClientInfo(it, true) }

        // 2 - ProjectDescription
        data.project?.let { repository.insertProjectDescription(it, true) }

        // 3 - OrganizationInfo
        data.organizationInfo?.let { repository.insertOrganizationInfo(it, true) }

        // 4 - OtherInfo
        data.otherInfo?.let { repository.insertOtherInfo(it, true) }

        // 5 - Devices
        data.deviceTemperatureCounters?.let { repository.insertDeviceTemperatureCounters(it, true) }
        data.deviceFlowTransducers?.let { repository.insertDeviceFlowTransducers(it, true) }
        data.deviceTemperatureTransducers?.let { repository.insertDeviceTemperatureTransducers(it, true) }
        data.devicePressureTransducers?.let { repository.insertDevicePressureTransducers(it, true) }
        data.deviceCounters?.let { repository.insertDeviceCounters(it, true) }
    }

    fun addPhoto(path: String, bitmap: Bitmap) {
        val projectFile = ProjectFile()
        projectFile.id = Random.nextInt(100000)
        projectFile.dataId = dataId
        projectFile.path = path
        projectFile.title = path.substring(path.lastIndexOf("/") + 1)
        projectFile.bitmap = bitmap
        projectFiles.add(projectFile)
    }

    fun syncBitmapWithPhotos(context: Context) {
//        if (photos.map{ it.bitmap }.size != photos.size) {
//            var i = 0
//
//            while (i < photos.size) {
//                val uri = photos[i]
//
//                val newBitmap = if (Build.VERSION.SDK_INT < 28) {
//                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//                } else {
//                    val decoder = ImageDecoder.createSource(context.contentResolver, uri)
//                    ImageDecoder.decodeBitmap(decoder)
//                }
//
//                if (newBitmap == null) {
//                    photos.removeAt(i)
//                } else {
//                    bitmaps.add(newBitmap)
//                    i++
//                }
//            }
//        }
    }

    fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? =
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

    fun getRealPathFromUri(context: Context, uri: Uri): String? {
        try {
            val filePathColumn = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
            )

            val returnCursor = context.contentResolver.query(uri, filePathColumn, null, null, null)

            if (returnCursor != null) {
                returnCursor.moveToFirst()
                val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                val name = returnCursor.getString(nameIndex)
                val folder = Utils.getDataIdFolder(context, dataId)
                val file = File(folder, name)
                val inputStream = context.contentResolver.openInputStream(uri)
                val outputStream = FileOutputStream(file)
                var read: Int
                val maxBufferSize = 1 * 1024 * 1024
                val bytesAvailable = inputStream!!.available()

                val bufferSize = min(bytesAvailable, maxBufferSize)
                val buffers = ByteArray(bufferSize)

                while (inputStream.read(buffers).also { read = it } != -1) {
                    outputStream.write(buffers, 0, read)
                }

                inputStream.close()
                outputStream.close()
                returnCursor.close()
                return file.absolutePath
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }

    private fun clearPhotoFiles(context: Context) =
        Utils.getDataIdFolder(context, dataId).deleteRecursively()

    // Пока не знаю как лучше, просто зануляю все поля и удаляю файлы из внутреннего хранилище
    fun dispose(context: Context, removeFiles: Boolean = true) {
        if (removeFiles) {
            clearPhotoFiles(context)
        }

        dataId = Random.nextInt(1, 100000)
        agreementNumber = ""
        name = ""
        addressUUTE = ""
        representativeName = ""
        phoneNumber = ""
        email = ""
        servingOrganization = ServingOrganization()
        hasDebt = false

        commentaryFiles =""
        matchesConditions =true

        devices.clear()

        deviceInQuestion = null
        deviceInfoInQuestion = null
        deviceShouldBeAdded = false

        projectFiles.clear()
        removeImageIndex = null
        showFilePreview = false
        filePreview = ProjectFile()
    }

    override fun onCleared() {
        super.onCleared()
        clearPhotoFiles(context)
    }
}
