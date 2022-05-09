package com.example.energo_monitoring.compose.viewmodels

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.data.ClientInfoWithProgress
import com.example.energo_monitoring.compose.data.SyncStatus
import com.example.energo_monitoring.checks.data.api.*
import com.example.energo_monitoring.checks.data.db.OtherInfo
import com.example.energo_monitoring.checks.data.db.ResultData
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.checks.activities.CheckMainActivity
import com.example.energo_monitoring.checks.presenters.utilities.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ChecksViewModel @Inject constructor() : ViewModel(){

    val clients = MutableStateFlow<List<ClientInfoWithProgress>>(emptyList())
    val isRefreshing = mutableStateOf(false)

    fun requestClientsInfo(context: Context, onComplete: () -> Unit) {
        val userId = SharedPreferencesManager.getUserId(context)
        ServerService.getService().getAvailableClientInfo(userId).enqueue(object : Callback<List<ClientInfo>> {
            override fun onResponse(call: Call<List<ClientInfo>>, response: Response<List<ClientInfo>>) {
                val clientsWithProgress = mutableListOf<ClientInfoWithProgress>()
                response.body()?.forEach {
                    val (progress, syncStatus) = getScreenSyncStatus(context, it.dataId)
                    clientsWithProgress.add(ClientInfoWithProgress(it, progress, syncStatus))
                }
                clientsWithProgress.sortByDescending { it.progress }
                clients.value = clientsWithProgress
                isRefreshing.value = false
                onComplete()
            }

            override fun onFailure(call: Call<List<ClientInfo>>, t: Throwable) {
                Toast.makeText(context, "Не удалось получить данные! Ошибка: "
                        + t.message, Toast.LENGTH_LONG).show()
                isRefreshing.value = false
                onComplete()
            }
        })
    }

    fun loadInfo(dataId: Int, clientName: String, onComplete: (Response<ClientDataBundle>) -> Unit, onError: (String) -> Unit) {
        ServerService.getService().getDetailedClientBundle(dataId)
            .enqueue(object : Callback<ClientDataBundle> {
                override fun onResponse(call: Call<ClientDataBundle>, response: Response<ClientDataBundle>) {
                    onComplete(response)
                }

                override fun onFailure(call: Call<ClientDataBundle>, t: Throwable) {
                    onError(t.message.orEmpty())
                }
            })
    }

    fun saveDataFromCloud(context: Context, dataId: Int, dataBundle: ClientDataBundle){
        val db = ResultDataDatabase.getDatabase(context)
        // 1 - ClientInfo
        val clientInfo = dataBundle.clientInfo.also { it.dataId = dataId }
        db.resultDataDAO().insertClientInfo(clientInfo)

        // 2 - ProjectDescription
        val projectDescription: ProjectDescription = dataBundle.project.also { it.dataId = dataId }
        projectDescription.files.forEach {
            it.dataId = dataId
            val file = File.createTempFile("projectFile", ".${it.extension}", context.filesDir)
            val fOut = FileOutputStream(file)
            fOut.flush()
            fOut.close()
            val path = FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider",
                file
            )
            it.path = path.toString()
        }
        db.resultDataDAO().insertProjectDescription(projectDescription)

        // 3 - OrganizationInfo
        val organizationInfo: OrganizationInfo = dataBundle.organizationInfo.also { it.dataId = dataId }
        db.resultDataDAO().insertOrganizationInfo(organizationInfo)

        // 4 - OtherInfo
        val otherInfo: OtherInfo = db.resultDataDAO().getOtherInfo(dataId) ?: OtherInfo(dataId)
        otherInfo.clientId = clientInfo.id
        otherInfo.organizationId = organizationInfo.id
        otherInfo.projectId = projectDescription.id
        otherInfo.localVersion = dataBundle.version
        otherInfo.cloudVersion = dataBundle.version
        otherInfo.userId = SharedPreferencesManager.getUserId(context)
        db.resultDataDAO().insertOtherInfo(otherInfo)
    }

    fun openCheck(context: Context, clientName: String, dataId: Int) {
        val intent = Intent(context, CheckMainActivity::class.java)
        intent.putExtra("dataId", dataId)
        intent.putExtra("clientName", clientName)
        context.startActivity(intent)
    }

    fun refresh(context: Context){
        isRefreshing.value = true
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
}