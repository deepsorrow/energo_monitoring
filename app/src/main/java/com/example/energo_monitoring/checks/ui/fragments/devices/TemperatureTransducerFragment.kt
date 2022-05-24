package com.example.energo_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer
import com.example.energo_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energo_monitoring.databinding.FragmentTemperatureTransducerBinding
import com.example.energo_monitoring.checks.ui.utils.AfterTextChangedListener
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.setMatchListener
import com.example.energo_monitoring.checks.ui.viewmodel.DeviceInspectionVM
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class TemperatureTransducerFragment : DaggerFragment() {

    @Inject
    @Named(VM_DEVICE_INSPECTION_VM)
    lateinit var viewModel: DeviceInspectionVM

    private lateinit var binding: FragmentTemperatureTransducerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTemperatureTransducerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceId = arguments?.getInt("deviceId") ?: 0
        val device = viewModel.devices[deviceId] as DeviceTemperatureTransducer
        binding.device = device

        val correctLength = device.length
        binding.length.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.length = s.toString()
                setMatchListener(binding.length, binding.lengthLayout, correctLength)
            }
        })

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)
        binding.installationPlaceSpinner.addTextChangedListener(object : AfterTextChangedListener {
            override fun afterTextChanged(s: Editable) {
                device.installationPlace = s.toString()
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
            TemperatureTransducerFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}