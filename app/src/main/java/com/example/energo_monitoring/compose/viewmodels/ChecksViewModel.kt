package com.example.energo_monitoring.compose.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.data.ClientInfoWithProgress
import com.example.energo_monitoring.data.api.ClientInfo
import com.example.energo_monitoring.data.api.ServerService
import com.example.energo_monitoring.data.db.ResultDataDatabase
import com.example.energo_monitoring.presentation.presenters.utilities.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ChecksViewModel @Inject constructor() : ViewModel(){

    val clients = MutableStateFlow<List<ClientInfo>>(emptyList())
    val clientsInProgress = MutableStateFlow<List<ClientInfoWithProgress>>(emptyList())
    val isRefreshing = mutableStateOf(false)

    fun requestClientsInfo(context: Context) {
        val userId = SharedPreferencesManager.getUserId(context)
        ServerService.getService().getAvailableClientInfo(userId).enqueue(object : Callback<List<ClientInfo>> {
            override fun onResponse(call: Call<List<ClientInfo>>, response: Response<List<ClientInfo>>) {
                val checksInProgressList = mutableListOf<ClientInfoWithProgress>()
                val clientsList = mutableListOf<ClientInfo>()

                response.body()?.forEach {
                    val progress = getProgress(context, it.dataId)
                    if (progress == -1) {
                        clientsList.add(it) // прогресса нет, отображаем в "новых"
                    } else {
                        checksInProgressList.add(ClientInfoWithProgress(it, progress))
                    }
                }
                clientsInProgress.value = checksInProgressList
                clients.value = clientsList
                isRefreshing.value = false
            }

            override fun onFailure(call: Call<List<ClientInfo>>, t: Throwable) {
                Toast.makeText(context, "Не удалось получить данные! Ошибка: "
                        + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun refresh(context: Context){
        isRefreshing.value = true
        requestClientsInfo(context)
    }

    fun getProgress(context: Context, id: Int): Int {
        val data = ResultDataDatabase.getDatabase(context).resultDataDAO().getData(id)
        data == null && return -1
        return data.otherInfo.currentScreen
    }
}