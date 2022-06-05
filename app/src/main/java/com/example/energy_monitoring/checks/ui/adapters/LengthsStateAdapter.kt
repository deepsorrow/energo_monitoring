package com.example.energy_monitoring.checks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energy_monitoring.checks.ui.fragments.CheckLengthFragment
import com.example.energy_monitoring.checks.ui.fragments.screens.HostCheckLengthsFragment
import javax.inject.Inject

class LengthsStateAdapter @Inject constructor(private val hostFragment: HostCheckLengthsFragment)
    : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = hostFragment.viewModel.devices.count()

    override fun createFragment(position: Int): Fragment =
        CheckLengthFragment.newInstance(position)
}