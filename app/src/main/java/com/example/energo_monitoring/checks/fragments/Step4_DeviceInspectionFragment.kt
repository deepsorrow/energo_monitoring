package com.example.energo_monitoring.checks.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.data.api.DeviceInfo
import com.example.energo_monitoring.databinding.FragmentStep4DeviceInspectionBinding
import com.example.energo_monitoring.checks.Utils
import com.example.energo_monitoring.checks.activities.CheckMainActivity
import com.example.energo_monitoring.checks.fragments.devices.adapters.DevicesStateAdapter
import com.example.energo_monitoring.checks.presenters.DeviceInspectionPresenter
import com.example.energo_monitoring.checks.presenters.utilities.SharedPreferencesManager
import com.example.energo_monitoring.checks.viewmodel.InspectionDeviceViewModel
import com.google.android.material.tabs.TabLayoutMediator

class Step4_DeviceInspectionFragment : Fragment() {

    var dataId = 0
    private lateinit var binding: FragmentStep4DeviceInspectionBinding
    lateinit var presenter: DeviceInspectionPresenter
    private lateinit var adapter: DevicesStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep4DeviceInspectionBinding.inflate(layoutInflater)

        val activity = requireActivity() as CheckMainActivity
        dataId = activity.dataId ?: 0
        activity.insertDestinationListener(R.id.step3_GeneralInspectionFragment) {
            presenter.insertDataToDb(dataId)
        }
        Utils.logProgress(requireContext(), 4, dataId)

        val clientDataBundle = SharedPreferencesManager.getClientDataBundle(requireContext())
        val devices = ArrayList<DeviceInfo>()
        devices.addAll(clientDataBundle.deviceTemperatureCounters)
        devices.addAll(clientDataBundle.deviceFlowTransducers)
        devices.addAll(clientDataBundle.deviceTemperatureTransducers)
        devices.addAll(clientDataBundle.devicePressureTransducers)

        if (devices.isEmpty()) {
            return binding.root
        }

        presenter = DeviceInspectionPresenter(this, devices)
        presenter.model = ViewModelProvider(this)[InspectionDeviceViewModel::class.java]

        adapter = DevicesStateAdapter(this)
        binding.devicesPager.adapter = adapter
        TabLayoutMediator(binding.devicesTabs, binding.devicesPager) { tab, position ->
            tab.text = presenter.devices[position].deviceName
        }.attach()

        return binding.root
    }

    fun getDevice(id: Int): DeviceInfo? {
        return presenter.devices[id]
    }

    fun getLastCheckDateListener(date: EditText?, device: DeviceInfo?): TextWatcher? {
        return presenter.getLastCheckDateTextWatcher(date, device)
    }
}