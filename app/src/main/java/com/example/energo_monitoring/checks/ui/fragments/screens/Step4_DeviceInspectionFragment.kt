package com.example.energo_monitoring.checks.ui.fragments.screens

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
import com.example.energo_monitoring.checks.ui.utils.Utils
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.checks.ui.adapters.DevicesStateAdapter
import com.example.energo_monitoring.checks.ui.presenters.DeviceInspectionPresenter
import com.example.energo_monitoring.checks.ui.viewmodel.InspectionDeviceViewModel
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
        dataId = activity.dataId

        Utils.logProgress(requireContext(), 4, dataId)

        val dao = ResultDataDatabase.getDatabase(context).resultDataDAO()
        val devices = mutableListOf<DeviceInfo>()
        devices.addAll(dao.getDeviceTemperatureCounter(dataId)?.let { list -> list.map { it as DeviceInfo } } ?: listOf())
        devices.addAll(dao.getDeviceFlowTransducers(dataId)?.let { list -> list.map { it as DeviceInfo } } ?: listOf())
        devices.addAll(dao.getDeviceTemperatureTransducers(dataId)?.let { list -> list.map { it as DeviceInfo } } ?: listOf())
        devices.addAll(dao.getDevicePressureTransducers(dataId)?.let { list -> list.map { it as DeviceInfo } } ?: listOf())

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