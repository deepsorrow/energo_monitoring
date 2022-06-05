package com.example.energy_monitoring.checks.ui.fragments.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.energy_monitoring.checks.di.modules.VM_TEMP_METRICS_VM
import com.example.energy_monitoring.checks.ui.fragments.screens.HostMetricsFragment
import com.example.energy_monitoring.checks.ui.adapters.MetricsListAdapter
import com.example.energy_monitoring.checks.ui.vm.TempCounterMetricsVM
import com.example.energy_monitoring.databinding.FragmentTempCounterMetricsBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class TempCounterMetricsFragment : DaggerFragment() {

    private var deviceId: Int = 0
    private lateinit var binding: FragmentTempCounterMetricsBinding

    @Inject
    @Named(VM_TEMP_METRICS_VM)
    lateinit var viewModel: TempCounterMetricsVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTempCounterMetricsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deviceId = arguments?.getInt("deviceId") ?: 0
        val device = (parentFragment as HostMetricsFragment).viewModel.tempCounters[deviceId]
        viewModel.device = device
        viewModel.initialize()

        binding.metricsList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.metricsList.adapter = MetricsListAdapter(viewModel.parameters) {
            viewModel.saveParameters(it)
        }
        viewModel.initizaled = true
    }

    companion object {
        @JvmStatic
        fun newInstance(deviceId: Int) =
            TempCounterMetricsFragment().apply {
                arguments = Bundle().apply {
                    putInt("deviceId", deviceId)
                }
            }
    }
}