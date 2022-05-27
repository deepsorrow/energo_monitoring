package com.example.energo_monitoring.checks.ui.fragments.screens

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
import com.example.energo_monitoring.checks.di.modules.VM_HOST_CHECK_LENGTH_VM
import com.example.energo_monitoring.databinding.FragmentStep3CheckLengthsBinding
import com.example.energo_monitoring.checks.ui.adapters.LengthsStateAdapter
import com.example.energo_monitoring.checks.ui.fragments.dialogs.AddNewDeviceDialog
import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager
import com.example.energo_monitoring.checks.ui.vm.HostCheckLengthVM
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
        viewModel.initialize()
        viewModel.addNewDevice = {
            AddNewDeviceDialog().show(parentFragmentManager, "AddNewDevice")
        }
        binding = FragmentStep3CheckLengthsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        adapter = LengthsStateAdapter(this)
        binding.checkLengthsPager.adapter = adapter
        TabLayoutMediator(binding.checkLengthTabs, binding.checkLengthsPager) { tab, position ->
            val device = viewModel.devices[position]
            tab.text = " ${device.deviceName.value.ifEmpty { device.deviceName.initialValue }}\n" +
                    "#${device.deviceNumber.value.ifEmpty { device.deviceNumber.initialValue }}"
        }.attach()

        initLauncher()

        return binding.root
    }

    fun takePhoto() {
        lastCreatedPath = LoadImageManager.getPhotoUri(requireContext()) {
            lastCreatedPathReal = it
        }
        LoadImageManager.takePhoto(takePhotoLauncher, lastCreatedPath)
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