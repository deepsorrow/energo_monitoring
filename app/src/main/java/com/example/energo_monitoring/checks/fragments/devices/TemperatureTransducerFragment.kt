package com.example.energo_monitoring.checks.fragments.devices

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer
import com.example.energo_monitoring.databinding.FragmentTemperatureTransducerBinding
import com.example.energo_monitoring.checks.fragments.Step4_DeviceInspectionFragment
import com.example.energo_monitoring.checks.fragments.devices.utils.DeviceUtils
import com.example.energo_monitoring.checks.fragments.devices.utils.DeviceUtils.initSpinner
import com.example.energo_monitoring.checks.fragments.devices.utils.DeviceUtils.setMatchListener

class TemperatureTransducerFragment : Fragment() {

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
        val device = (requireParentFragment() as Step4_DeviceInspectionFragment).getDevice(deviceId) as DeviceTemperatureTransducer
        binding.device = device

        val correctLength = device.length
        binding.length.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.length = s.toString()
                setMatchListener(binding.length, binding.lengthLayout, correctLength)
            }
        })

        initSpinner(view, listOf("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner)
        binding.installationPlaceSpinner.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                device.installationPlace = s.toString()
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

        DeviceUtils.setDeviceNameNumberMatchListener(view, device)

        val listener = (parentFragment as Step4_DeviceInspectionFragment).getLastCheckDateListener(binding.lastCheckDate, device)
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