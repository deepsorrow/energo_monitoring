package com.example.energo_monitoring.compose.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.data.api.ClientInfo
import com.example.energo_monitoring.data.api.ServerService
import com.example.energo_monitoring.presentation.presenters.utilities.SharedPreferencesManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ChecksViewModel @Inject constructor() : ViewModel(){

    val clients = MutableStateFlow<List<ClientInfo>>(emptyList())

    fun getClients(context: Context): LiveData<List<ClientInfo>> {
        val result = MutableLiveData<List<ClientInfo>>()

        val userId = SharedPreferencesManager.getUserId(context)
        ServerService.getService().getAvailableClientInfo(userId).enqueue(object : Callback<List<ClientInfo>> {
            override fun onResponse(call: Call<List<ClientInfo>>, response: Response<List<ClientInfo>>) {
                clients.value = response.body()!!
            }

            override fun onFailure(call: Call<List<ClientInfo>>, t: Throwable) {
                Toast.makeText(context, "Не удалось получить данные! Ошибка: "
                        + t.message, Toast.LENGTH_LONG).show()
            }
        })

        return result
    }
}