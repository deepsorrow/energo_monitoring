package com.example.energo_monitoring.data.api;

import com.example.energo_monitoring.data.api.ServerApi;
import com.example.energo_monitoring.presentation.presenters.utilities.Settings;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerService {
    private static ServerApi api;

    public static ServerApi getService(){
        if(api == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + Settings.server_ip + ":" + Settings.server_port + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();

            api = retrofit.create(ServerApi.class);
        }

        return api;
    }

    private static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                //.addInterceptor(logging)
                .build();

        return httpClient;
    }
}
