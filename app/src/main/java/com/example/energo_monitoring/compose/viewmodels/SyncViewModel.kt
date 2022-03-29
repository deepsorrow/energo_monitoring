package com.example.energo_monitoring.compose.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.NewClientInfo
import com.example.energo_monitoring.compose.repo.Repository
import com.example.energo_monitoring.data.api.ServerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncViewModel : ViewModel() {

    fun isUpToDate(): Boolean {
        return true
    }

    fun syncAgreements(context: Context){
        val repository = Repository.getRepository(context = context)

//        ServerService.getService()?.allAgreements?.enqueue(object : Callback<List<NewClientInfo>>{
//            override fun onResponse(
//                call: Call<List<NewClientInfo>>,
//                response: Response<List<NewClientInfo>>
//            ) {
//                val body = response.body()
//                body?.let {
//                    it.forEach { clientInfo ->
//                        repository.insertClientInfo(clientInfo = clientInfo) }
//                }
//            }
//
//            override fun onFailure(call: Call<List<NewClientInfo>>, t: Throwable) {
//                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
//            }
//        })
    }
}