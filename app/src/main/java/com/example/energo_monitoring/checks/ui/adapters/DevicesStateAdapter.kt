package com.example.energo_monitoring.checks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energo_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energo_monitoring.checks.ui.fragments.screens.DeviceInspectionFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.FlowTransducerFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.PressureTransducerFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.TemperatureCounterFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.TemperatureTransducerFragment
import com.example.energo_monitoring.checks.ui.vm.DeviceInspectionVM
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Named

class DevicesStateAdapter @Inject constructor (
    @Named(VM_DEVICE_INSPECTION_VM) private val viewModel: DeviceInspectionVM,
    hostFragment: DeviceInspectionFragment
) : FragmentStateAdapter(hostFragment) {

    override fun getItemCount(): Int = viewModel.devices.count()

    override fun createFragment(deviceId: Int): Fragment {
        val device = viewModel.devices[deviceId]
        return when (device.typeId) {
            1 -> TemperatureCounterFragment.newInstance(deviceId)
            2 -> FlowTransducerFragment.newInstance(deviceId)
            3 -> TemperatureTransducerFragment.newInstance(deviceId)
            4 -> PressureTransducerFragment.newInstance(deviceId)
            else -> throw IllegalArgumentException("Неверный deviceId: $deviceId")
        }
    }
}