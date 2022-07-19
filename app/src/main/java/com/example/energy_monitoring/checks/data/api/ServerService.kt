package com.example.energy_monitoring.checks.data.api

import android.util.Log
import com.example.energy_monitoring.checks.ui.utils.Settings
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServerService {
    private var api: ServerApi? = null
    @JvmStatic
    val service: ServerApi
        get() {
            if (api == null) {
                val loggingInterceptor = HttpLoggingInterceptor { message ->
                    longInfo(message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                val gson = GsonBuilder()
                    .setLenient() // для работы byteArray
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://" + Settings.server_ip + ":" + Settings.server_port + "/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()

                api = retrofit.create(ServerApi::class.java)
            }
            return api!!
        }

    fun resetApi() {
        api = null
    }

    fun longInfo(str: String) {
        if (str.length > 4000) {
            Log.d("OkHttp3Request", str.substring(0, 4000))
            longInfo(str.substring(4000))
        } else Log.d("", str)
    }

}