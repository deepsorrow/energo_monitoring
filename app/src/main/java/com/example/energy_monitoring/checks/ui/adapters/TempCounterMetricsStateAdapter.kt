package com.example.energy_monitoring.checks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energy_monitoring.checks.ui.fragments.screens.HostMetricsFragment
import com.example.energy_monitoring.checks.ui.fragments.devices.TempCounterMetricsFragment

class TempCounterMetricsStateAdapter(private val hostFragment: HostMetricsFragment)
    : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = hostFragment.viewModel.tempCounters.count()

    override fun createFragment(deviceId: Int): Fragment =
        TempCounterMetricsFragment.newInstance(deviceId)
}