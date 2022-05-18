package com.example.energo_monitoring.compose.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.energo_monitoring.checks.data.api.ServerApi
import com.example.energo_monitoring.compose.data.api.RefDoc
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class RefDocsVM @Inject constructor(
    @ApplicationContext // не утекает, т.к. applicationContext
    private val context: Context,
    private val serverApi: ServerApi
 ) : ViewModel() {
    private var refDocs = mutableStateListOf<RefDoc>()

    fun getRefDocs() = runBlocking {
        launch {
            try {
                val response = serverApi.allRefDocsWithoutData.awaitResponse()
                if (response.isSuccessful) {
                    refDocs.clear()
                    refDocs.addAll(response.body() ?: mutableStateListOf())
                }
            } catch (t: Throwable) {
                Toast.makeText(context, "Код ошибки: 102. $t", Toast.LENGTH_LONG).show()
            }
        }
    }

}