package com.example.energo_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils
import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energo_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energo_monitoring.databinding.FragmentFlowTransducerBinding
import com.example.energo_monitoring.checks.ui.fragments.screens.Step4_DeviceInspectionFragment
import com.example.energo_monitoring.checks.ui.utils.AfterTextChangedListener
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.setMatchListener
import com.example.energo_monitoring.checks.ui.viewmodel.DeviceInspectionVM
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class FlowTransducerFragment @Inject constructor() : DaggerFragment() {

    @Inject
    @Named(VM_DEVICE_INSPECTION_VM)
    lateinit var viewModel: DeviceInspectionVM

    private lateinit var binding: FragmentFlowTransducerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowTransducerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceId = arguments?.getInt("deviceId") ?: 0
        val device = viewModel.devices[deviceId] as DeviceFlowTransducer

        binding.device = device

        val correctDiameter = device.diameter
        binding.diameter.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.diameter = s.toString()
                setMatchListener(binding.diameter, binding.diameterLayout, correctDiameter)
            }
        })

        val correctImpulseWeight = device.impulseWeight
        binding.impulseWeight.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.impulseWeight = s.toString()
                setMatchListener(binding.impulseWeight, binding.impulseWeightLayout, correctImpulseWeight)
            }
        })

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)
        initSpinner(view, listOf("Взлет", "Теплоком", "Термотроник"), R.id.manufacturerSpinner)

        binding.installationPlaceSpinner.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.installationPlace = s.toString()
            }
        })

        binding.manufacturerSpinner.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.manufacturer = s.toString()
            }
        })

        binding.values.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.values = s.toString()
            }
        })

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
            FlowTransducerFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}