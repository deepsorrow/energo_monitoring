package com.example.energo_monitoring.compose.viewmodels

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.checks.data.api.*
import com.example.energo_monitoring.checks.data.db.OtherInfo
import com.example.energo_monitoring.checks.data.db.ResultData
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.checks.domain.repo.ResultDataRepository
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.presenters.utilities.SharedPreferencesManager
import com.example.energo_monitoring.compose.data.ClientInfoWithProgress
import com.example.energo_monitoring.compose.data.SyncStatus
import com.example.energo_monitoring.compose.screens.mainMenu.checks.DropDownClientActions
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ChecksViewModel @Inject constructor(
    private val serverApi: ServerApi
) : ViewModel() {

    var clients by mutableStateOf<List<ClientInfoWithProgress>>(emptyList())
    var isRefreshing by mutableStateOf(false)

    fun requestClientsInfo(context: Context, onComplete: () -> Unit) {
        val userId = SharedPreferencesManager.getUserId(context)
        serverApi.getAvailableClientInfo(userId).enqueue(object : Callback<List<ClientInfo>> {
            override fun onResponse(call: Call<List<ClientInfo>>, response: Response<List<ClientInfo>>) {
                val clientsWithProgress = mutableListOf<ClientInfoWithProgress>()
                response.body()?.forEach {
                    val (progress, syncStatus) = getScreenSyncStatus(context, it.dataId)
                    clientsWithProgress.add(ClientInfoWithProgress(it, progress, syncStatus))
                }
                clientsWithProgress.sortByDescending { it.progress }
                clients = clientsWithProgress
                isRefreshing = false
                onComplete()
            }

            override fun onFailure(call: Call<List<ClientInfo>>, t: Throwable) {
                Toast.makeText(context, "Не удалось получить данные! Ошибка: "
                        + t.message, Toast.LENGTH_LONG).show()
                isRefreshing = false
                onComplete()
            }
        })
    }

    fun loadInfo(dataId: Int, onComplete: (ClientDataBundle?) -> Unit, onError: (String) -> Unit) {
        serverApi.getDetailedClientBundle(dataId)
            .enqueue(object : Callback<ClientDataBundle> {
                override fun onResponse(call: Call<ClientDataBundle>, response: Response<ClientDataBundle>) {
                    onComplete(response.body())
                }

                override fun onFailure(call: Call<ClientDataBundle>, t: Throwable) {
                    onError(t.message.orEmpty())
                }
            })
    }

    fun saveDataFromCloud(context: Context, dataId: Int, dataBundle: ClientDataBundle){

        val repository = ResultDataRepository(ResultDataDatabase.getDatabase(context).resultDataDAO())
        // 1 - ClientInfo
        val clientInfo = dataBundle.clientInfo.also { it.dataId = dataId }
        repository.insertClientInfo(clientInfo, true)

        // 2 - ProjectDescription
        val projectDescription: ProjectDescription = dataBundle.project.also { it.dataId = dataId }
        projectDescription.files.forEach {
            if (it.extension == "png" || it.extension == "jpg") {
                it.dataId = dataId

                it.dataBase64 = it.dataBase64.replace("data:image/png;base64,", "")
                    .replace("data:image/jpeg;base64,", "")
                    .replace("data:image/gif;base64,", "")
                val decodedString: ByteArray = Base64.decode(it.dataBase64, Base64.DEFAULT)

                val file = File.createTempFile("projectFile_", ".${it.extension}", context.filesDir)
                val fOut = FileOutputStream(file)

                val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                val compressFormat = when (it.extension) {
                    "png" -> Bitmap.CompressFormat.PNG
                    "jpg" -> Bitmap.CompressFormat.JPEG
                    else -> Bitmap.CompressFormat.WEBP
                }
                bitmap.compress(compressFormat, 100, fOut);

                fOut.flush()
                fOut.close()
                it.path = file.absolutePath
            } else if (it.extension == "pdf") {

            }
        }
        repository.insertProjectDescription(projectDescription, true)

        // 3 - OrganizationInfo
        val organizationInfo: OrganizationInfo = dataBundle.organizationInfo.also { it.dataId = dataId }
        repository.insertOrganizationInfo(organizationInfo, true)

        // 4 - OtherInfo
        val otherInfo: OtherInfo = repository.getOtherInfo(dataId) ?: OtherInfo(dataId)
        otherInfo.clientId = clientInfo.id
        otherInfo.organizationId = organizationInfo.id
        otherInfo.projectId = projectDescription.id
        otherInfo.localVersion = dataBundle.version
        otherInfo.cloudVersion = dataBundle.version
        otherInfo.userId = SharedPreferencesManager.getUserId(context)
        repository.insertOtherInfo(otherInfo, true)

        // 5 - Devices
        dataBundle.deviceTemperatureCounters.forEach { it.dataId = dataId }
        dataBundle.deviceFlowTransducers.forEach { it.dataId = dataId }
        dataBundle.deviceTemperatureTransducers.forEach { it.dataId = dataId }
        dataBundle.devicePressureTransducers.forEach { it.dataId = dataId }
        dataBundle.deviceCounters.forEach { it.dataId = dataId }

        repository.insertDeviceTemperatureCounters(dataBundle.deviceTemperatureCounters, true)
        repository.insertDeviceFlowTransducers(dataBundle.deviceFlowTransducers, true)
        repository.insertDeviceTemperatureTransducers(dataBundle.deviceTemperatureTransducers, true)
        repository.insertDevicePressureTransducers(dataBundle.devicePressureTransducers, true)
        repository.insertDeviceCounters(dataBundle.deviceCounters, true)
    }

    fun openCheck(context: Context, clientName: String, dataId: Int) {
        val intent = Intent(context, CheckMainActivity::class.java)
        intent.putExtra("dataId", dataId)
        intent.putExtra("clientName", clientName)
        context.startActivity(intent)
    }

    fun refresh(context: Context){
        isRefreshing = true
        requestClientsInfo(context) {}
    }

    fun getScreenSyncStatus(context: Context, id: Int): Pair<Int, SyncStatus> {
        val data = ResultDataDatabase.getDatabase(context).resultDataDAO().getData(id)
        data == null && return 0 to SyncStatus.NOT_LOADED
        var status = SyncStatus.NOT_LOADED
        if (data!!.otherInfo != null && data.otherInfo?.dataId != 0) {
           status = if (data.otherInfo.cloudVersion == data.otherInfo.localVersion) SyncStatus.SYNCED else SyncStatus.NOT_SYNCED
        }
        return data.otherInfo.currentScreen to status
    }

    fun getSyncStatus(data: ResultData): SyncStatus {
        var status = SyncStatus.NOT_LOADED
        if (data.otherInfo != null && data.otherInfo?.clientId != 0) {
            status = if (data.otherInfo.cloudVersion == data.otherInfo.localVersion) SyncStatus.SYNCED else SyncStatus.NOT_SYNCED
        }
        return status
    }

    fun onActionClicked(action: DropDownClientActions, clientInfo: ClientInfo) {
        when(action) {
            DropDownClientActions.SYNC -> {

            }
            DropDownClientActions.REMOVE_LOCALLY -> {

            }
            DropDownClientActions.REMOVE_GLOBALLY -> {

            }
        }
    }
}