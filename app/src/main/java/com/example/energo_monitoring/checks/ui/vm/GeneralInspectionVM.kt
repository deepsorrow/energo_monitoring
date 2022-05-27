package com.example.energo_monitoring.checks.ui.vm

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.energo_monitoring.checks.data.files.ProjectFile
import com.example.energo_monitoring.checks.data.api.ClientInfo
import com.example.energo_monitoring.checks.data.db.OtherInfo
import com.example.energo_monitoring.checks.ui.adapters.ProjectPhotoAdapter
import com.example.energo_monitoring.checks.ui.vm.base.BaseScreenVM
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random


class GeneralInspectionVM @Inject constructor(
    application: Application,
) : BaseScreenVM(3, application) {

    var currentPreviewFile: MutableLiveData<ProjectFile?> = MutableLiveData()

    val agreementNumber = ObservableField("")
    val organizationName = ObservableField("")
    val address = ObservableField("")
    val email = ObservableField("")
    val contactPerson = ObservableField("")
    val phone = ObservableField("")

    val currentLightValue: MutableLiveData<Boolean?> = MutableLiveData()
    var lightIsOk: Boolean? = false
        set(value) {
            val saveAction = { otherInfo: OtherInfo ->
                otherInfo.lightIsOk = value
                otherInfo
            }
            saveOtherInfoField(saveAction, "Не удалось сохранить соотв. освещения! Код ошибки: 891")
            currentLightValue.value = value
            field = value
        }

    val currentSanPin: MutableLiveData<Boolean?> = MutableLiveData()
    var sanPinIsOk: Boolean? = false
        set(value) {
            val saveAction = { otherInfo: OtherInfo ->
                otherInfo.sanPinIsOk = value
                otherInfo
            }
            saveOtherInfoField(saveAction, "Не удалось сохранить соотв. сан пин! Код ошибки: 892")
            currentSanPin.value = value
            field = value
        }

    var comment = ObservableField("")

    val projectFiles by lazy { repository.getProjectDescription(dataId)?.files }

    override fun initialize() {
        val otherInfo = repository.getOtherInfo(dataId)
        val clientInfo = repository.getClientInfo(dataId)
        if (clientInfo != null && otherInfo != null) {
            agreementNumber.set(clientInfo.agreementNumber)
            organizationName.set(clientInfo.name)
            address.set(clientInfo.addressUUTE)
            email.set(clientInfo.email)
            contactPerson.set(clientInfo.representativeName)
            phone.set(clientInfo.phoneNumber)

            lightIsOk = otherInfo.lightIsOk
            sanPinIsOk = otherInfo.sanPinIsOk
            comment.set(otherInfo.generalInspectionComment)
        } else {
            Toast.makeText(context, "Нет данных! Код ошибки: 134", Toast.LENGTH_LONG).show()
        }
    }

    fun saveProjectPhoto(result: ActivityResult, adapter: ProjectPhotoAdapter, lastCreatedPath: String, onSuccess: () -> Unit) {
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val projectFile = ProjectFile()
                projectFile.id = 101 + Random.nextInt(100000)
                projectFile.dataId = dataId
                projectFile.path = lastCreatedPath
                projectFile.title = lastCreatedPath.substring(lastCreatedPath.lastIndexOf("/") + 1)
                val insertResult = repository.insertProjectFile(projectFile)
                if (insertResult.isSuccess) {
                    projectFiles?.let {
                        it.add(projectFile)
                        adapter.notifyItemChanged(it.count())
                        onSuccess()
                    }
                } else {
                    Toast.makeText(context, "Не удалось сохранить файл!", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    fun onAgreementNumChanged() {
        val saveAction = { clientInfo: ClientInfo ->
            clientInfo.agreementNumber = agreementNumber.get()
            clientInfo
        }
        saveClientInfoField(saveAction, "Номер договора не был сохранен!")
    }

    fun onOrganizationNameChanged() {
        val saveAction = { clientInfo: ClientInfo ->
            clientInfo.name = organizationName.get()
            clientInfo
        }
        saveClientInfoField(saveAction, "Имя организации не было сохранено!")
    }

    fun onAddressChanged() {
        val saveAction = { clientInfo: ClientInfo ->
            clientInfo.addressUUTE = address.get()
            clientInfo
        }
        saveClientInfoField(saveAction, "Адрес не был сохранен!")
    }

    fun onEmailChanged() {
        val saveAction = { clientInfo: ClientInfo ->
            clientInfo.email = email.get()
            clientInfo
        }
        saveClientInfoField(saveAction, "Электронная почта не была сохранена!")
    }

    fun onContactPersonChanged() {
        val saveAction = { clientInfo: ClientInfo ->
            clientInfo.representativeName = contactPerson.get()
            clientInfo
        }
        saveClientInfoField(saveAction, "Номер контакного лица не был сохранен!")
    }

    fun onPhoneChanged() {
        val saveAction = { clientInfo: ClientInfo ->
            clientInfo.phoneNumber = phone.get()
            clientInfo
        }
        saveClientInfoField(saveAction, "Номер телефона не был сохранен!")
    }

    fun onCommentChanged() {
        val saveAction = { otherInfo: OtherInfo ->
            otherInfo.generalInspectionComment = comment.get().orEmpty()
            otherInfo
        }
        saveOtherInfoField(saveAction, "Нет данных! Код ошибки: 817")
    }

    private fun saveClientInfoField(saveAction: (ClientInfo) -> ClientInfo, errorMsg: String) {
        val clientInfo = repository.getClientInfo(dataId)
        if (clientInfo != null) {
            saveAction(clientInfo)
            repository.insertClientInfo(clientInfo)
        } else {
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveOtherInfoField(saveAction: (OtherInfo) -> OtherInfo, errorMsg: String) {
        val otherInfo = repository.getOtherInfo(dataId)
        if (otherInfo != null) {
            saveAction(otherInfo)
            repository.insertOtherInfo(otherInfo)
        } else {
            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
        }
    }

    fun addProjectFile() {

    }
}