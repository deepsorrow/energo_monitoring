package com.example.energy_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energy_monitoring.R
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils
import com.example.energy_monitoring.checks.di.modules.VM_FLOW_TRANSDUCER_VM
import com.example.energy_monitoring.checks.ui.fragments.devices.base.BaseDeviceFragment
import com.example.energy_monitoring.databinding.FragmentFlowTransducerBinding
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.addOnPropertyChanged
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.addTo
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils.setMatchListener
import com.example.energy_monitoring.checks.ui.vm.devices.FlowTransducerVM
import javax.inject.Inject
import javax.inject.Named

class FlowTransducerFragment @Inject constructor() : BaseDeviceFragment() {

    @Inject
    @Named(VM_FLOW_TRANSDUCER_VM)
    lateinit var viewModel: FlowTransducerVM

    private lateinit var binding: FragmentFlowTransducerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentFlowTransducerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.initialize(deviceId)

        viewModel.diameter.addOnPropertyChanged {
            setMatchListener(binding.diameterLayout, viewModel.device.diameter.value, viewModel.device.diameter.initialValue)
        }.addTo(disposables)

        viewModel.impulseWeight.addOnPropertyChanged {
            setMatchListener(binding.diameterLayout, viewModel.device.impulseWeight.value, viewModel.device.impulseWeight.initialValue)
        }.addTo(disposables)

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)
        initSpinner(view, listOf("Взлет", "Теплоком", "Термотроник"), R.id.manufacturerSpinner)

        DeviceUtils.setDeviceNameNumberMatchListener(view, viewModel.device.deviceName, viewModel.device.deviceNumber)

        viewModel.initialized = true
        //val listener = viewModel.lastCheckDateTextWatcher(binding.lastCheckDate, device)
        //binding.lastCheckDate.addTextChangedListener(listener)
    }

    companion object {
        @JvmStatic
        fun newInstance(deviceId: Int) =
            FlowTransducerFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}