package com.example.energy_monitoring.checks.ui.fragments.screens

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.energy_monitoring.checks.di.modules.VM_HOST_CHECK_LENGTH_VM
import com.example.energy_monitoring.databinding.FragmentStep3CheckLengthsBinding
import com.example.energy_monitoring.checks.ui.adapters.LengthsStateAdapter
import com.example.energy_monitoring.checks.ui.fragments.dialogs.AddNewDeviceDialog
import com.example.energy_monitoring.checks.ui.utils.LoadImageManager
import com.example.energy_monitoring.checks.ui.vm.hosts.HostCheckLengthVM
import com.example.energy_monitoring.compose.DeviceType
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class HostCheckLengthsFragment : DaggerFragment() {

    private lateinit var binding: FragmentStep3CheckLengthsBinding

    lateinit var takePhotoLauncher: ActivityResultLauncher<Intent>
    var lastCreatedPath: Uri? = null
    var lastCreatedPathReal: String = ""

    var takePhotoCallback: ((Bitmap, String) -> Unit)? = null

    @Named(VM_HOST_CHECK_LENGTH_VM)
    @Inject
    lateinit var viewModel: HostCheckLengthVM

    @Inject
    lateinit var adapter: LengthsStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStep3CheckLengthsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        initDevices()

        viewModel.addNewDevice = {
            val onDeviceAddedCallback = {
                initDevices()
                binding.checkLengthsPager.setCurrentItem(viewModel.devices.count() - 1, true)
            }
            AddNewDeviceDialog(DeviceType.FlowTransducer, onDeviceAddedCallback).show(parentFragmentManager, "AddNewDevice")
        }

        initLauncher()

        return binding.root
    }

    fun takePhoto() {
        lastCreatedPath = LoadImageManager.getPhotoUri(requireContext(), viewModel.dataId) {
            lastCreatedPathReal = it
        }
        LoadImageManager.takePhoto(takePhotoLauncher, lastCreatedPath)
    }

    private fun initDevices() {
        viewModel.initialized = false
        viewModel.initialize()

        adapter = LengthsStateAdapter(this)
        binding.checkLengthsPager.adapter = adapter
        TabLayoutMediator(binding.checkLengthTabs, binding.checkLengthsPager) { tab, position ->
            val deviceName = viewModel.devices[position].deviceName
            val deviceNumber = viewModel.devices[position].deviceNumber
            tab.text = "${deviceName.getValue()}\n#${deviceNumber.getValue()}"
        }.attach()
    }

    private fun initLauncher() {
        takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val photo = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        lastCreatedPath
                    )
                    takePhotoCallback?.let { it(photo, lastCreatedPathReal) }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "$e", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}