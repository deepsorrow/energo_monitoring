package com.example.energo_monitoring.checks.fragments.devices

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer
import com.example.energo_monitoring.databinding.FragmentPressureTransducerBinding
import com.example.energo_monitoring.checks.fragments.Step4_DeviceInspectionFragment
import com.example.energo_monitoring.checks.fragments.devices.utils.DeviceUtils
import com.example.energo_monitoring.checks.fragments.devices.utils.DeviceUtils.initSpinner
import com.example.energo_monitoring.checks.fragments.devices.utils.DeviceUtils.setMatchListener

class PressureTransducerFragment : Fragment() {

    private lateinit var binding: FragmentPressureTransducerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPressureTransducerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deviceId = arguments?.getInt("deviceId") ?: 0
        val device = (requireParentFragment() as Step4_DeviceInspectionFragment).getDevice(deviceId) as DevicePressureTransducer
        binding.device = device

        val correctSensorRange = device.sensorRange
        binding.sensorRange.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.sensorRange = s.toString()
                setMatchListener(
                    binding.sensorRange,
                    binding.sensorRangeLayout,
                    correctSensorRange
                )
            }
        })

        binding.installationPlaceSpinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.installationPlace = s.toString()
            }
        })

        binding.manufacturerSpinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.manufacturer = s.toString()
            }
        })

        binding.values.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.values = s.toString()
            }
        })

        binding.comment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.comment = s.toString()
            }
        })

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)
        initSpinner(view, listOf("НПК ВИП", "Тепловодохран"), R.id.manufacturerSpinner)

        DeviceUtils.setDeviceNameNumberMatchListener(view, device)

        val listener = (parentFragment as Step4_DeviceInspectionFragment).getLastCheckDateListener(binding.lastCheckDate, device)
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