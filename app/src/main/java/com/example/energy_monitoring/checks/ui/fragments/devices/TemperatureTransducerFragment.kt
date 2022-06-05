package com.example.energy_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energy_monitoring.R
import com.example.energy_monitoring.checks.di.modules.VM_TEMPERATURE_TRANSDUCER_VM
import com.example.energy_monitoring.checks.ui.fragments.devices.base.BaseDeviceFragment
import com.example.energy_monitoring.databinding.FragmentTemperatureTransducerBinding
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.addOnPropertyChanged
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.addTo
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.setMatchListener
import com.example.energy_monitoring.checks.ui.vm.devices.TemperatureTransducerVM
import javax.inject.Inject
import javax.inject.Named

class TemperatureTransducerFragment : BaseDeviceFragment() {

    @Inject
    @Named(VM_TEMPERATURE_TRANSDUCER_VM)
    lateinit var viewModel: TemperatureTransducerVM

    private lateinit var binding: FragmentTemperatureTransducerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTemperatureTransducerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.initialize(deviceId)

        viewModel.length.addOnPropertyChanged {
            setMatchListener(binding.lengthLayout, viewModel.device.length.value, viewModel.device.length.initialValue)
        }.addTo(disposables)

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)

        DeviceUtils.setDeviceNameNumberMatchListener(view, viewModel.device.deviceName, viewModel.device.deviceNumber)

        viewModel.initialized = true
        //val listener = viewModel.lastCheckDateTextWatcher(binding.lastCheckDate, device)
        //binding.lastCheckDate.addTextChangedListener(listener)
    }

    companion object {
        @JvmStatic
        fun newInstance(deviceId: Int) =
            TemperatureTransducerFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}