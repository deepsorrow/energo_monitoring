package com.example.energo_monitoring.checks.data.api;

import com.example.energo_monitoring.checks.data.db.ResultData;
import com.example.energo_monitoring.compose.data.api.RefDoc;

import java.sql.Ref;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {

    @GET("/api/v1/getAvailableClientInfo")
    Call<List<ClientInfo>> getAvailableClientInfo(@Query("userId") int userId);

    @GET("/api/v1/getDetailedClientBundle")
    Call<ClientDataBundle> getDetailedClientBundle(@Query("id") int id);

    @POST("/api/v1/authorize")
    Call<AuthorizeResponse> authorize(@Body AuthorizeBody body);

    @POST("/api/v1/sendResults")
    Call<Boolean> sendResults(@Body ResultData resultData);

    @GET("/api/v1/getAllAgreements")
    Call<List<ClientInfo>> getAllAgreements();

    @GET("api/v1/refDocById")
    Call<RefDoc> getRefDocById(@Query("id") int id);

    @GET("api/v1/refDocsWithoutData")
    Call<List<RefDoc>> getAllRefDocsWithoutData();
}
