package com.example.energo_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energo_monitoring.checks.di.modules.VM_PRESSURE_TRANSDUCER_VM
import com.example.energo_monitoring.checks.ui.fragments.devices.base.BaseDeviceFragment
import com.example.energo_monitoring.databinding.FragmentPressureTransducerBinding
import com.example.energo_monitoring.checks.ui.utils.AfterTextChangedListener
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.addOnPropertyChanged
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.addTo
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.initSpinner
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils.setMatchListener
import com.example.energo_monitoring.checks.ui.viewmodel.devices.PressureTransducerVM
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
        binding = FragmentPressureTransducerBinding.inflate(layoutInflater)
        val t = mapOf<String, String>()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceId = arguments?.getInt("deviceId") ?: 0
        val device = parentViewModel.devices[deviceId] as DevicePressureTransducer
        binding.viewModel = viewModel

        viewModel.sensorRange.addOnPropertyChanged {
            val value = viewModel.device.sensorRange.value
            val correctValue = viewModel.device.sensorRange.correctValue
            setMatchListener(binding.sensorRangeLayout, value, correctValue)
        }.addTo(disposables)

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)
        initSpinner(view, listOf("НПК ВИП", "Тепловодохран"), R.id.manufacturerSpinner)

        DeviceUtils.setDeviceNameNumberMatchListener(viewModel, device)

        val listener = parentViewModel.getLastCheckDateTextWatcher(binding.lastCheckDate, device)
        binding.lastCheckDate.addTextChangedListener(listener)
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