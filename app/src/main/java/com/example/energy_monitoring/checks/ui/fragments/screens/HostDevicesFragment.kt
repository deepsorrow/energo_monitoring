package com.example.energy_monitoring.checks.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energy_monitoring.databinding.FragmentStep2DeviceInspectionBinding
import com.example.energy_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energy_monitoring.checks.ui.adapters.DevicesStateAdapter
import com.example.energy_monitoring.checks.ui.fragments.dialogs.AddNewDeviceDialog
import com.example.energy_monitoring.checks.ui.vm.hosts.HostDevicesVM
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class HostDevicesFragment : DaggerFragment() {

    private lateinit var binding: FragmentStep2DeviceInspectionBinding

    @Inject
    @Named(VM_DEVICE_INSPECTION_VM)
    lateinit var viewModel: HostDevicesVM

    @Inject
    lateinit var adapter: DevicesStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep2DeviceInspectionBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        initDevices()

        viewModel.addNewDevice = {
            val onDeviceAddedCallback = {
                initDevices()
                binding.devicesPager.setCurrentItem(viewModel.devices.count() - 1, true)
            }
            AddNewDeviceDialog(onDeviceAddedCallback = onDeviceAddedCallback).show(parentFragmentManager, "AddNewDevice")
        }

        return binding.root
    }

    private fun initDevices() {
        viewModel.initialized = false
        viewModel.initialize()

        binding.devicesPager.adapter = adapter
        TabLayoutMediator(binding.devicesTabs, binding.devicesPager) { tab, position ->
            val deviceName = viewModel.devices[position].deviceName
            val deviceNumber = viewModel.devices[position].deviceNumber
            tab.text = "${deviceName.getValue()}\n#${deviceNumber.getValue()}"
        }.attach()
    }
}