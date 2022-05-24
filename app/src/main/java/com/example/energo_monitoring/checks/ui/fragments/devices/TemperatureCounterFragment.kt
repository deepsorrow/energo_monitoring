package com.example.energo_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureCounter
import com.example.energo_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energo_monitoring.databinding.FragmentTemperatureCounterBinding
import com.example.energo_monitoring.checks.ui.fragments.screens.Step4_DeviceInspectionFragment
import com.example.energo_monitoring.checks.ui.utils.AfterTextChangedListener
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energo_monitoring.checks.ui.viewmodel.DeviceInspectionVM
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class TemperatureCounterFragment : DaggerFragment() {

    @Inject
    @Named(VM_DEVICE_INSPECTION_VM)
    lateinit var viewModel: DeviceInspectionVM

    private lateinit var binding: FragmentTemperatureCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTemperatureCounterBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceId = arguments?.getInt("deviceId") ?: 0
        val device = viewModel.devices[deviceId] as DeviceTemperatureCounter

        binding.device = device

        initSpinner(view, listOf("Отопление", "ГВС", "Пар", "Вентиляция"), R.id.unitSystemSpinner)

        binding.unitSystemSpinner.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.unitSystem = s.toString()
            }
        }
        )
        binding.modification.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.modification = s.toString()
            }
        }
        )
        binding.interval.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.interval = s.toString()
            }
        }
        )
        binding.comment.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.comment = s.toString()
            }
        })

        DeviceUtils.setDeviceNameNumberMatchListener(view, device)

        val listener = viewModel.getLastCheckDateTextWatcher(binding.lastCheckDate, device)
        binding.lastCheckDate.addTextChangedListener(listener)
    }

    companion object {
        @JvmStatic
        fun newInstance(deviceId: Int) =
            TemperatureCounterFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}