package com.example.energo_monitoring.checks.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.energo_monitoring.checks.ui.fragments.CheckLengthFragment
import com.example.energo_monitoring.checks.ui.fragments.screens.Step5_CheckLengthsFragment

class LengthsStateAdapter(private val hostFragment: Step5_CheckLengthsFragment)
    : FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = hostFragment.presenter.devices.count()

    override fun createFragment(position: Int): Fragment =
        CheckLengthFragment.newInstance(position)
}