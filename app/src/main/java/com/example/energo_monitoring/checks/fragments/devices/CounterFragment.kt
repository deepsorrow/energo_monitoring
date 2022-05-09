package com.example.energo_monitoring.checks.fragments.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.energo_monitoring.databinding.FragmentCounterBinding
import com.example.energo_monitoring.checks.fragments.Step6_TempMetricsFragment
import com.example.energo_monitoring.checks.adapters.TemperatureCounterValuesMainListAdapter


class CounterFragment : Fragment() {

    private var deviceId: Int = 0
    private lateinit var binding: FragmentCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        deviceId = arguments?.getInt("deviceId") ?: 0
        binding = FragmentCounterBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parameters = (parentFragment as Step6_TempMetricsFragment).getParameters(deviceId)
        binding.metricsList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.metricsList.adapter = TemperatureCounterValuesMainListAdapter(parameters)
    }

    companion object {
        @JvmStatic
        fun newInstance(deviceId: Int) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}