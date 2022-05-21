package com.example.energo_monitoring.checks.data.api

import com.example.energo_monitoring.checks.ui.presenters.utilities.Settings
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServerService {
    private var api: ServerApi? = null
    @JvmStatic
    val service: ServerApi
        get() {
            if (api == null) {
                val gson = GsonBuilder()
                    .setLenient() // для работы byteArray
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://" + Settings.server_ip + ":" + Settings.server_port + "/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                api = retrofit.create(ServerApi::class.java)
            }
            return api!!
        }

}