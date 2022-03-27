package com.example.feature_settings.login

import androidx.lifecycle.MutableLiveData
import com.example.feature_settings.AuthorizeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TempCallback(val loginResult: MutableLiveData<AuthorizeResponse?>) : Callback<AuthorizeResponse> {
    override fun onResponse(call: Call<AuthorizeResponse?>, response: Response<AuthorizeResponse?>) {
        loginResult.value = response.body()
    }

    override fun onFailure(call: Call<AuthorizeResponse?>, t: Throwable) {
        loginResult.value = AuthorizeResponse(false, t.toString())
    }
}