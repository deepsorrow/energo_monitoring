package com.example.energo_monitoring.data.api;

import com.example.energo_monitoring.data.AuthorizeBody;
import com.example.energo_monitoring.data.AuthorizeResponse;
import com.example.energo_monitoring.data.db.ResultData;

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
}
