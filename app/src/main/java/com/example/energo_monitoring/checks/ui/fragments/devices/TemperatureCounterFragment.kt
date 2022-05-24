package com.example.energo_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.di.modules.VM_TEMPERATURE_COUNTER_VM
import com.example.energo_monitoring.checks.ui.fragments.devices.base.BaseDeviceFragment
import com.example.energo_monitoring.databinding.FragmentTemperatureCounterBinding
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energo_monitoring.checks.ui.vm.devices.TemperatureCounterVM
import javax.inject.Inject
import javax.inject.Named

class TemperatureCounterFragment : BaseDeviceFragment() {

    @Inject
    @Named(VM_TEMPERATURE_COUNTER_VM)
    lateinit var viewModel: TemperatureCounterVM

    private lateinit var binding: FragmentTemperatureCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTemperatureCounterBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.initialize(deviceId)

        initSpinner(view, listOf("Отопление", "ГВС", "Пар", "Вентиляция"), R.id.unitSystemSpinner)

        DeviceUtils.setDeviceNameNumberMatchListener(view, viewModel.device.deviceName, viewModel.device.deviceNumber)

        //val listener = viewModel.lastCheckDateTextWatcher(binding.lastCheckDate, device)
        //binding.lastCheckDate.addTextChangedListener(listener)
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