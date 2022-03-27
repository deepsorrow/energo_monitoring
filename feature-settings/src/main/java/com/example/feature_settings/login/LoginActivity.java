//package com.example.feature_settings.login;
//
//import android.app.Activity;
//
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//
//import com.example.energo_monitoring.databinding.ActivityLoginBinding;
//import com.example.energo_monitoring.data.AuthorizeResponse;
//import com.example.energo_monitoring.presentation.presenters.utilities.SharedPreferencesManager;
//import com.example.energo_monitoring.presentation.activities.TestMainActivity;
//import com.google.android.material.snackbar.Snackbar;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private ActivityLoginBinding binding;
//    private LoginViewModel loginViewModel;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityLoginBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        if(!SharedPreferencesManager.getUsername(this).equals(""))
//            startMainActivity();
//
//        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//
//        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
//            if (loginFormState == null) {
//                return;
//            }
//            binding.login.setEnabled(loginFormState.isDataValid());
//            if (loginFormState.getUsernameError() != null) {
//                binding.username.setError(getString(loginFormState.getUsernameError()));
//            }
//            if (loginFormState.getPasswordError() != null) {
//                binding.password.setError(getString(loginFormState.getPasswordError()));
//            }
//        });
//
//        loginViewModel.getLoginResult().observe(this, loginResult -> {
//            if (loginResult == null) {
//                return;
//            }
//            binding.loading.setVisibility(View.GONE);
//            if (loginResult.isSuccess())
//                updateUiWithUser(loginResult.getUser().getName(), loginResult.getUser().getId());
//            else
//                showLoginFailed(loginResult.getError());
//        });
//
//        TextWatcher afterTextChangedListener = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//            @Override
//            public void afterTextChanged(Editable s) {
//                loginViewModel.loginDataChanged(binding.username.getText().toString(),
//                        binding.password.getText().toString());
//            }
//        };
//        binding.username.addTextChangedListener(afterTextChangedListener);
//        binding.password.addTextChangedListener(afterTextChangedListener);
//        binding.password.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                loginViewModel.login(binding.username.getText().toString(),
//                        binding.password.getText().toString());
//            }
//            return false;
//        });
//
//        binding.login.setOnClickListener(v -> {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
//
//            binding.loading.setVisibility(View.VISIBLE);
//            loginViewModel.login(binding.username.getText().toString(),
//                    binding.password.getText().toString());
//        });
//    }
//
//    private void updateUiWithUser(String username, int userId) {
//        SharedPreferencesManager.saveUsername(this, username);
//        SharedPreferencesManager.saveUserId(this, userId);
//
//        startMainActivity();
//    }
//
//    private void startMainActivity(){
//        Intent intent = new Intent(this, TestMainActivity.class);
//        startActivity(intent);
//
//        setResult(Activity.RESULT_OK);
//        finish();
//    }
//
//    private void showLoginFailed(String errorString) {
//        Snackbar.make(binding.login, errorString,
//                3000).show();
//    }
//}