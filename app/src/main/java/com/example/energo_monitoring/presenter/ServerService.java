package com.example.energo_monitoring.presenter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerService {
    private static ServerApi api;

    public static ServerApi getService(){
        if(api == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.16.0.3:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(ServerApi.class);
        }

        return api;
    }
}
