package com.example.energo_monitoring.checks.fragments.devices.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energo_monitoring.checks.fragments.Step6_TempMetricsFragment
import com.example.energo_monitoring.checks.fragments.devices.CounterFragment

class CountersStateAdapter(private val hostFragment: Step6_TempMetricsFragment)
    : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = hostFragment.presenter.devices.count()

    override fun createFragment(deviceId: Int): Fragment =
        CounterFragment.newInstance(deviceId)
}