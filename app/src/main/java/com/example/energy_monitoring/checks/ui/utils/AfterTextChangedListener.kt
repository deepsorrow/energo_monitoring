package com.example.energy_monitoring.checks.ui.utils

import android.text.TextWatcher

// Класс нужен чтобы не писать необходимые beforeTextChanged, onTextChanged постоянно
interface AfterTextChangedListener : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}