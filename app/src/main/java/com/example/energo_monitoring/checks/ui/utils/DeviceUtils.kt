package com.example.energo_monitoring.checks.ui.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.Observable
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

object DeviceUtils {
    fun setMatchListener(layout: TextInputLayout, currentValue: String, correctValue: String) {
        val match = currentValue == correctValue
        if (!match && correctValue != null && correctValue.isNotEmpty()) {
            layout.error = "По проекту должно быть $correctValue!"
        } else {
            layout.isErrorEnabled = false
        }
    }

    fun initSpinner(view: View?, values: List<String>, spinnerId: Int) {
        if (view != null) {
            val adapter = ArrayAdapter(view.context, R.layout.list_item_spinner, values)
            val dropdownButton = view.findViewById<AutoCompleteTextView>(spinnerId)
            dropdownButton?.setAdapter(adapter)
        }
    }

    fun setDeviceNameNumberMatchListener(deviceView: View, device: DeviceInfo) {
        val deviceName: TextInputEditText = deviceView.findViewById(R.id.deviceName)
        val deviceNameLayout: TextInputLayout = deviceView.findViewById(R.id.deviceNameLayout)
        val correctName = device.deviceName
        deviceName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.deviceName = s.toString()
                setMatchListener(deviceName, deviceNameLayout, correctName)
            }
        })
        val deviceNumber: TextInputEditText = deviceView.findViewById(R.id.deviceNumber)
        val deviceNumberLayout: TextInputLayout = deviceView.findViewById(R.id.deviceNumberLayout)
        val correctDeviceNumber = device.deviceNumber
        deviceNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.deviceNumber = s.toString()
                setMatchListener(deviceNumber, deviceNumberLayout, correctDeviceNumber)
            }
        })
    }

    fun <T: Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable?, i: Int) =
                callback(observable as T)
        }.also { addOnPropertyChangedCallback(it) }.let {
            Disposables.fromAction {removeOnPropertyChangedCallback(it)}
        }

    fun Disposable.addTo(disposables: CompositeDisposable) {
        disposables.add(this)
    }
}