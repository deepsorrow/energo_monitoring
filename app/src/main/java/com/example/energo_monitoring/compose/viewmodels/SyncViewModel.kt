package com.example.energo_monitoring.compose.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.compose.domain.db.Repository
import com.example.energo_monitoring.checks.data.api.ClientInfo

class SyncViewModel : ViewModel() {
    val checks: List<ClientInfo> = emptyList()
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