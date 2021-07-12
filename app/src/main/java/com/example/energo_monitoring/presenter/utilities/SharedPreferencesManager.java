package com.example.energo_monitoring.presenter.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesManager {

    public static ClientDataBundle getClientDataBundle(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("currentClientDataBundle", "");

        return gson.fromJson(json, ClientDataBundle.class);
    }

    public static void saveClientDataBundle(Context context, ClientDataBundle clientDataBundle){
        Gson gson = new Gson();
        String json = gson.toJson(clientDataBundle);

        SharedPreferences.Editor prefsEditor =
                context.getSharedPreferences("data", MODE_PRIVATE).edit();
        prefsEditor.putString("currentClientDataBundle", json);
        prefsEditor.commit();
    }

    public static void saveUsername(Context context, String username){
        SharedPreferences.Editor prefsEditor =
                context.getSharedPreferences("data", MODE_PRIVATE).edit();
        prefsEditor.putString("username", username);
        prefsEditor.commit();
    }

    public static String getUsername(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("data", MODE_PRIVATE);
        return mPrefs.getString("username", "");
    }

    public static void clearUsername(Context context){
        SharedPreferences.Editor prefsEditor =
                context.getSharedPreferences("data", MODE_PRIVATE).edit();
        prefsEditor.putString("username", "");
        prefsEditor.commit();
    }
}
