package com.example.feature_settings

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.feature_settings.databinding.FragmentLoginBinding
import com.example.feature_settings.domain.SharedPreferencesManager

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        if (SharedPreferencesManager.getUsername(requireContext()) != "")
            startMainActivity()

        loginViewModel = ViewModelProvider(this).get<LoginViewModel>(
            LoginViewModel::class.java
        )

        loginViewModel.getLoginFormState()
            .observe(this, Observer<LoginFormState> { loginFormState: LoginFormState? ->
                if (loginFormState == null) {
                    return@observe
                }
                binding.login.setEnabled(loginFormState.isDataValid())
                if (loginFormState.getUsernameError() != null) {
                    binding.username.setError(getString(loginFormState.getUsernameError()))
                }
                if (loginFormState.getPasswordError() != null) {
                    binding.password.setError(getString(loginFormState.getPasswordError()))
                }
            })

        loginViewModel.getLoginResult().observe(this,
            Observer<Any?> { loginResult ->
                if (loginResult == null) {
                    return@Observer
                }
                binding.loading.setVisibility(View.GONE)
                if (loginResult.isSuccess()) updateUiWithUser(
                    loginResult.getUser().getName(),
                    loginResult.getUser().getId()
                ) else showLoginFailed(loginResult.getError())
            })

        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    binding.username.getText().toString(),
                    binding.password.getText().toString()
                )
            }
        }
        binding.username.addTextChangedListener(afterTextChangedListener)
        binding.password.addTextChangedListener(afterTextChangedListener)
        binding.password.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    binding.username.getText().toString(),
                    binding.password.getText().toString()
                )
            }
            false
        }

        binding.login.setOnClickListener { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0)
            binding.loading.setVisibility(View.VISIBLE)
            loginViewModel.login(
                binding.username.getText().toString(),
                binding.password.getText().toString()
            )
        }

        return binding.root
    }
}