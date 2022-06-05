package com.example.energy_monitoring.checks.ui.activities;

import android.app.Activity;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.example.energy_monitoring.checks.data.api.AuthorizeBody;
import com.example.energy_monitoring.checks.data.api.AuthorizeResponse;
import com.example.energy_monitoring.checks.data.api.ServerService;
import com.example.energy_monitoring.compose.activities.CreatingNew1Activity;
import com.example.energy_monitoring.databinding.ActivityLoginBinding;
import com.example.energy_monitoring.checks.ui.utils.SharedPreferencesManager;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!SharedPreferencesManager.getUsername(this).equals(""))
            startMainActivity();

        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login(binding.username.getText().toString(), binding.password.getText().toString());
            }
            return false;
        });

        binding.login.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

            binding.loading.setVisibility(View.VISIBLE);
            login(binding.username.getText().toString(), binding.password.getText().toString());
        });
    }

    private void login(String username, String password){
        ServerService.getService().authorize(new AuthorizeBody(username, password)).enqueue(
                new Callback<AuthorizeResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthorizeResponse> call,
                                           @NonNull Response<AuthorizeResponse> response) {
                        AuthorizeResponse body = response.body();
                        if (body != null && body.isSuccess()) {
                            updateUiWithUser(body.getUser().getName(), body.getUser().getId());
                            startMainActivity();
                        } else {
                            showLoginFailed("Некорректный ответ! Код ошибки: 271");
                        }
                        binding.loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(@NonNull Call<AuthorizeResponse> call,
                                          @NonNull Throwable t) {
                        showLoginFailed(t.toString());
                        binding.loading.setVisibility(View.GONE);
                    }
                });
    }

    private void updateUiWithUser(String username, int userId) {
        SharedPreferencesManager.saveUsername(this, username);
        SharedPreferencesManager.saveUserId(this, userId);

        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, CreatingNew1Activity.class);
        startActivity(intent);

        setResult(Activity.RESULT_OK);
        finish();
    }

    private void showLoginFailed(String errorString) {
        Snackbar.make(binding.login, errorString,3000).show();
    }
}