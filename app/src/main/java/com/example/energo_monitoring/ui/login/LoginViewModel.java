package com.example.energo_monitoring.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.model.api.AuthorizeBody;
import com.example.energo_monitoring.model.api.AuthorizeResponse;
import com.example.energo_monitoring.presenter.ServerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<AuthorizeResponse> loginResult = new MutableLiveData<>();

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<AuthorizeResponse> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        ServerService.getService().authorize(new AuthorizeBody(username, password)).enqueue(
                new Callback<AuthorizeResponse>() {
            @Override
            public void onResponse(Call<AuthorizeResponse> call, Response<AuthorizeResponse> response) {
                loginResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<AuthorizeResponse> call, Throwable t) {
                loginResult.setValue(new AuthorizeResponse(false, t.toString()));
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null;
    }
}