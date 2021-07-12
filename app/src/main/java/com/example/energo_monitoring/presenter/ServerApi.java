package com.example.energo_monitoring.presenter;

import com.example.energo_monitoring.model.api.AuthorizeBody;
import com.example.energo_monitoring.model.api.AuthorizeResponse;
import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.model.api.ClientInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {

    @GET("/api/v1/getAvailableClientInfo")
    Call<List<ClientInfo>> getAvailableClientInfo();

    @GET("/api/v1/getDetailedClientBundle")
    Call<ClientDataBundle> getDetailedClientBundle(@Query("id") Long id);

    @POST("/api/v1/authorize")
    Call<AuthorizeResponse> authorize(@Body AuthorizeBody body);
}
