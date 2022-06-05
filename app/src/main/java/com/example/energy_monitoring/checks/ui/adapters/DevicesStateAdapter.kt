package com.example.energy_monitoring.checks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energy_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energy_monitoring.checks.ui.fragments.screens.HostDevicesFragment
import com.example.energy_monitoring.checks.ui.fragments.devices.FlowTransducerFragment
import com.example.energy_monitoring.checks.ui.fragments.devices.PressureTransducerFragment
import com.example.energy_monitoring.checks.ui.fragments.devices.TemperatureCounterFragment
import com.example.energy_monitoring.checks.ui.fragments.devices.TemperatureTransducerFragment
import com.example.energy_monitoring.checks.ui.vm.hosts.HostDevicesVM
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Named

class DevicesStateAdapter @Inject constructor (
    @Named(VM_DEVICE_INSPECTION_VM) private val viewModel: HostDevicesVM,
    hostFragment: HostDevicesFragment
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