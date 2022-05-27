package com.example.energo_monitoring.checks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energo_monitoring.checks.ui.fragments.screens.TempMetricsFragment
import com.example.energo_monitoring.checks.ui.fragments.devices.CounterFragment

class CountersStateAdapter(private val hostFragment: TempMetricsFragment)
    : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = hostFragment.presenter.devices.count()

    override fun createFragment(deviceId: Int): Fragment =
        CounterFragment.newInstance(deviceId)
}