package com.example.energo_monitoring.checks.data.api

import retrofit2.http.GET
import retrofit2.http.POST
import com.example.energo_monitoring.checks.data.db.ResultData
import com.example.energo_monitoring.compose.data.api.RefDoc
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Query

interface ServerApi {
    @GET("/api/v1/getAvailableClientInfo")
    fun getAvailableClientInfo(@Query("userId") userId: Int): Call<List<ClientInfo>>

    @GET("/api/v1/getDetailedClientBundle")
    fun getDetailedClientBundle(@Query("id") id: Int): Call<ClientDataBundle>

    @POST("/api/v1/authorize")
    fun authorize(@Body body: AuthorizeBody): Call<AuthorizeResponse>

    @POST("/api/v1/sendResults")
    fun sendResults(@Body resultData: ResultData): Call<Boolean>

    @GET("/api/v1/getAllAgreements")
    fun allAgreements(): Call<List<ClientInfo>?>

    @GET("api/v1/refDocById")
    fun getRefDocById(@Query("id") id: Int): Call<RefDoc>

    @GET("api/v1/refDocsWithoutData")
    fun allRefDocsWithoutData(): Call<List<RefDoc>?>
}