package com.example.energo_monitoring.checks.fragments.devices.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energo_monitoring.checks.fragments.Step4_DeviceInspectionFragment
import com.example.energo_monitoring.checks.fragments.devices.FlowTransducerFragment
import com.example.energo_monitoring.checks.fragments.devices.PressureTransducerFragment
import com.example.energo_monitoring.checks.fragments.devices.TemperatureCounterFragment
import com.example.energo_monitoring.checks.fragments.devices.TemperatureTransducerFragment
import java.lang.IllegalArgumentException

class DevicesStateAdapter(private val hostFragment: Step4_DeviceInspectionFragment)
    : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = hostFragment.presenter.devices.count()

    override fun createFragment(deviceId: Int): Fragment {
        val device = hostFragment.presenter.devices[deviceId]
        return when (device.typeId) {
            1 -> TemperatureCounterFragment.newInstance(deviceId)
            2 -> FlowTransducerFragment.newInstance(deviceId)
            3 -> TemperatureTransducerFragment.newInstance(deviceId)
            4 -> PressureTransducerFragment.newInstance(deviceId)
            else -> throw IllegalArgumentException("Неверный deviceId: $deviceId")
        }
    }
}