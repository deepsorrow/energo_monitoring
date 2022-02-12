package com.example.feature_create_new_data.domain

import com.example.feature_create_new_data.data.api.ServerApi
import com.example.feature_create_new_data.util.Constants.SERVER_IP
import com.example.feature_create_new_data.util.Constants.SERVER_PORT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerService {
    var api: ServerApi? = null

    fun getService(): ServerApi? {
        if (api == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://$SERVER_IP:$SERVER_PORT/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit.create(ServerApi::class.java)
        }
        return api
    }
}