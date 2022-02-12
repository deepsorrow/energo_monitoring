package com.example.feature_create_new_data.presentation.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.feature_create_new_data.data.ClientInfo
import com.example.feature_create_new_data.domain.ServerService
import com.example.feature_create_new_data.domain.repositories.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncViewModel : ViewModel() {

    fun isUpToDate(): Boolean {
        return true
    }

    fun syncAgreements(context: Context){
        val repository = Repository.getRepository(context = context)

        ServerService.getService()?.getAllAgreements()?.enqueue(object : Callback<List<ClientInfo>>{
            override fun onResponse(
                call: Call<List<ClientInfo>>,
                response: Response<List<ClientInfo>>
            ) {
                val body = response.body()
                body?.let {
                    it.forEach { clientInfo ->
                        repository.insertClientInfo(clientInfo = clientInfo) }
                }
            }

            override fun onFailure(call: Call<List<ClientInfo>>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}