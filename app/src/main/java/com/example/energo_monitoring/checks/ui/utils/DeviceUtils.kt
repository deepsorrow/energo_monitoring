package com.example.energo_monitoring.checks.ui.utils

import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.Observable
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.devices.Field
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

object DeviceUtils {
    fun setMatchListener(layout: TextInputLayout, currentValue: String, correctValue: String) {
        val match = currentValue == correctValue
        if (!match && correctValue.isNotEmpty()) {
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

    fun setDeviceNameNumberMatchListener(deviceView: View, deviceName: Field, deviceNumber: Field) {
        val nameView = deviceView.findViewById<TextInputEditText>(R.id.deviceName)
        val nameLayout = deviceView.findViewById<TextInputLayout>(R.id.deviceNameLayout)
        val correctName = deviceName.initialValue
        nameView.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                setMatchListener(nameLayout, deviceName.value, correctName)
            }
        })

        val numberView = deviceView.findViewById<TextInputEditText>(R.id.deviceNumber)
        val numberLayout = deviceView.findViewById<TextInputLayout>(R.id.deviceNumberLayout)
        val correctNumber = deviceNumber.initialValue
        numberView.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                setMatchListener(numberLayout, deviceNumber.value, correctNumber)
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