package com.example.energy_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energy_monitoring.R
import com.example.energy_monitoring.checks.di.modules.VM_PRESSURE_TRANSDUCER_VM
import com.example.energy_monitoring.checks.ui.fragments.devices.base.BaseDeviceFragment
import com.example.energy_monitoring.databinding.FragmentPressureTransducerBinding
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.addOnPropertyChanged
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.addTo
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.setMatchListener
import com.example.energy_monitoring.checks.ui.vm.devices.PressureTransducerVM
import javax.inject.Inject
import javax.inject.Named

class PressureTransducerFragment : BaseDeviceFragment() {

    @Inject
    @Named(VM_PRESSURE_TRANSDUCER_VM)
    lateinit var viewModel: PressureTransducerVM

    private lateinit var binding: FragmentPressureTransducerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPressureTransducerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.initialize(deviceId)

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)
        initSpinner(view, listOf("НПК ВИП", "Тепловодохран"), R.id.manufacturerSpinner)

        viewModel.sensorRange.addOnPropertyChanged {
            setMatchListener(
                layout = binding.sensorRangeLayout,
                currentValue = viewModel.device.sensorRange.value,
                correctValue = viewModel.device.sensorRange.initialValue
            )
        }.addTo(disposables)

        DeviceUtils.setDeviceNameNumberMatchListener(
            deviceView = view,
            deviceName = viewModel.device.deviceName,
            deviceNumber = viewModel.device.deviceNumber
        )

        viewModel.initialized = true
        //val listener = parentViewModel.getLastCheckDateTextWatcher(binding.lastCheckDate, device)
        //binding.lastCheckDate.addTextChangedListener(listener)
    }

    companion object {
        @JvmStatic
        fun newInstance(deviceId: Int) =
            PressureTransducerFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}