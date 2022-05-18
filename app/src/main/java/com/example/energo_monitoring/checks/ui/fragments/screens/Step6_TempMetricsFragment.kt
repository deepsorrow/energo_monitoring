package com.example.energo_monitoring.checks.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.TempParameter
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import com.example.energo_monitoring.databinding.FragmentStep6TempMetricsBinding
import com.example.energo_monitoring.checks.ui.utils.Utils
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.adapters.CountersStateAdapter
import com.example.energo_monitoring.checks.ui.presenters.TemperatureCounterCharacteristicsPresenter
import com.example.energo_monitoring.checks.ui.viewmodel.TemperatureCounterViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.ArrayList


class Step6_TempMetricsFragment : Fragment() {

    lateinit var presenter: TemperatureCounterCharacteristicsPresenter
    private lateinit var binding: FragmentStep6TempMetricsBinding
    private lateinit var model: TemperatureCounterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep6TempMetricsBinding.inflate(layoutInflater)

        val activity = requireActivity() as CheckMainActivity
        val dataId = activity.dataId ?: 0
//        activity.insertDestinationListener(R.id.step5_CheckLengthsFragment) {
//            presenter.insertDataToDb(dataId, binding.temperatureCounterComment.getText().toString())
//        }

        Utils.logProgress(requireContext(), 6, dataId)

        model = ViewModelProvider(this)[TemperatureCounterViewModel::class.java]

        presenter = TemperatureCounterCharacteristicsPresenter(activity, dataId, model)

        val adapter = CountersStateAdapter(this)
        binding.metricsPager.adapter = adapter
        TabLayoutMediator(binding.metricsTabs, binding.metricsPager) { tab, position ->
            tab.text = presenter.devices[position].deviceName
        }.attach()

        return binding.root
    }

    fun getDevice(id: Int): DeviceInfo? {
        return presenter.devices[id]
    }

    fun getParameters(id: Int): ArrayList<TempParameter> {
        return presenter.parameters[id]
    }
}