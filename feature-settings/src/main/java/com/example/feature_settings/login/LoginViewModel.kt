package com.example.feature_settings.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.common.AuthorizeBody
import com.example.common.UserData
import com.example.common.api.ServerService
import com.example.feature_settings.AuthorizeResponse
import com.example.feature_settings.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    class AuthorizeResponse {
        var isSuccess: Boolean
        var user: UserData? = null
        var error = ""

        constructor(success: Boolean, user: UserData?) {
            isSuccess = success
            this.user = user
        }

        constructor(success: Boolean, error: String) {
            isSuccess = success
            this.error = error
        }
    }

    val loginFormState = MutableLiveData<LoginFormState>()
    val loginResult = MutableLiveData<AuthorizeResponse?>()
    fun getLoginFormState(): LiveData<LoginFormState> {
        return loginFormState
    }

    fun getLoginResult(): LiveData<AuthorizeResponse?> {
        return loginResult
    }

    fun login(username: String?, password: String?) {
        // can be launched in a separate asynchronous job
        val auth = object : Callback<AuthorizeResponse> {
            override fun onResponse(call: Call<AuthorizeResponse>, response: Response<AuthorizeResponse>) {
                loginResult.value = response.body()
            }

            override fun onFailure(call: Call<AuthorizeResponse>, t: Throwable) {
                loginResult.value = AuthorizeResponse(false, t.toString())
            }
        }
        ServerService.getService().authorize(AuthorizeBody(username, password)).enqueue(auth)
    }

    fun loginDataChanged(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(LoginFormState(R.string.invalid_username, null))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(LoginFormState(null, R.string.invalid_password))
        } else {
            loginFormState.setValue(LoginFormState(true))
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String?): Boolean {
        if (username == null) {
            return false
        }
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            !username.trim { it <= ' ' }.isEmpty()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password != null
    }
}
