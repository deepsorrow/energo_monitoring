package com.example.energo_monitoring.checks.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energo_monitoring.databinding.FragmentStep4DeviceInspectionBinding
import com.example.energo_monitoring.checks.di.modules.VM_DEVICE_INSPECTION_VM
import com.example.energo_monitoring.checks.ui.adapters.DevicesStateAdapter
import com.example.energo_monitoring.checks.ui.vm.DeviceInspectionVM
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class Step4_DeviceInspectionFragment : DaggerFragment() {

    private lateinit var binding: FragmentStep4DeviceInspectionBinding

    @Inject
    @Named(VM_DEVICE_INSPECTION_VM)
    lateinit var viewModel: DeviceInspectionVM

    @Inject
    lateinit var adapter: DevicesStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep4DeviceInspectionBinding.inflate(layoutInflater)

        viewModel.initialize()

        binding.devicesPager.adapter = adapter
        TabLayoutMediator(binding.devicesTabs, binding.devicesPager) { tab, position ->
            if (viewModel.devices[position].deviceName.value.isNotEmpty())
                tab.text = viewModel.devices[position].deviceName.value
            else
                tab.text = viewModel.devices[position].deviceName.initialValue
        }.attach()

        return binding.root
    }
}