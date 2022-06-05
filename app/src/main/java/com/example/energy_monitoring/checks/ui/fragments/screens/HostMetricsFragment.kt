package com.example.energy_monitoring.checks.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.energy_monitoring.checks.di.modules.VM_HOST_METRICS_VM
import com.example.energy_monitoring.databinding.FragmentStep4TempMetricsBinding
import com.example.energy_monitoring.checks.ui.adapters.TempCounterMetricsStateAdapter
import com.example.energy_monitoring.checks.ui.fragments.dialogs.AddNewDeviceDialog
import com.example.energy_monitoring.checks.ui.vm.hosts.HostMetricsVM
import com.example.energy_monitoring.compose.DeviceType
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class HostMetricsFragment : DaggerFragment() {

    @Inject
    @Named(VM_HOST_METRICS_VM)
    lateinit var viewModel: HostMetricsVM

    private lateinit var binding: FragmentStep4TempMetricsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep4TempMetricsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        initDevices()

        viewModel.addNewDevice = {
            val onDeviceAddedCallback = {
                initDevices()
                binding.metricsPager.setCurrentItem(viewModel.tempCounters.count() - 1, true)
            }
            AddNewDeviceDialog(DeviceType.TemperatureCounter, onDeviceAddedCallback).show(parentFragmentManager, "AddNewDevice")
        }

        return binding.root
    }

    private fun initDevices() {
        viewModel.initialized = false
        viewModel.initialize()

        val adapter = TempCounterMetricsStateAdapter(this)
        binding.metricsPager.adapter = adapter
        TabLayoutMediator(binding.metricsTabs, binding.metricsPager) { tab, position ->
            val deviceName = viewModel.tempCounters[position].deviceName
            val deviceNumber = viewModel.tempCounters[position].deviceNumber
            tab.text = "${deviceName.getValue()}\n#${deviceNumber.getValue()}"
        }.attach()
    }
}