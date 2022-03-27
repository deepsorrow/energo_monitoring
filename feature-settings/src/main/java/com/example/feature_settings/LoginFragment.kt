package com.example.feature_settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.feature_settings.login.LoginViewModel
import com.example.feature_settings.databinding.FragmentLoginBinding
import com.example.feature_settings.domain.SharedPreferencesManager
import com.example.feature_settings.presentation.MainActivity
import com.example.feature_settings.login.LoginFormState
import com.google.android.material.snackbar.Snackbar
import com.example.feature_settings.AuthorizeResponse

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var activity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        activity = requireActivity() as MainActivity
        if (SharedPreferencesManager.getUsername(requireContext()) != "")
            startMainActivity()

        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(activity) { loginFormState ->
                if (loginFormState == null) {
                    return@observe
                }
            binding.login.isEnabled = loginFormState.isDataValid
                if (loginFormState.usernameError != null) {
                    binding.username.error = getString(loginFormState.usernameError!!)
                }
                if (loginFormState.passwordError != null) {
                    binding.password.error = getString(loginFormState.passwordError!!)
                }
            }

        loginViewModel.loginResult.observe(activity) { loginResult: AuthorizeResponse? ->
            binding.loading.visibility = View.GONE
            if (loginResult != null) {
                if (loginResult.isSuccess)
                    updateUiWithUser(loginResult.user.name, loginResult.user.id)
                else
                    showLoginFailed(loginResult.error)
            }
        }

        val afterTextChangedListener: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }
        binding.username.addTextChangedListener(afterTextChangedListener)
        binding.password.addTextChangedListener(afterTextChangedListener)
        binding.password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
            false
        }

        binding.login.setOnClickListener {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()!!.getWindowToken(), 0)
            binding.loading.visibility = View.VISIBLE
            loginViewModel.login(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        return binding.root
    }

    private fun updateUiWithUser(username: String, userId: Int) {
        SharedPreferencesManager.saveUsername(activity, username);
        SharedPreferencesManager.saveUserId(activity, userId);

        startMainActivity();
    }


    private fun showLoginFailed(errorString: String) {
        Snackbar.make(binding.login, errorString,3000).show();
    }

    private fun startMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }
}