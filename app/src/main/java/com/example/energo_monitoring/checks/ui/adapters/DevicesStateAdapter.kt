package com.example.energo_monitoring.checks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energo_monitoring.checks.ui.fragments.screens.Step4_DeviceInspectionFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.FlowTransducerFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.PressureTransducerFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.TemperatureCounterFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.TemperatureTransducerFragment
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