package com.example.feature_create_new_data.data.api

import com.example.feature_create_new_data.data.ClientInfo
import retrofit2.Call
import retrofit2.http.GET

interface ServerApi {
    @GET("/api/v1/getAllAgreements")
    fun getAllAgreements(): Call<List<ClientInfo>>
}