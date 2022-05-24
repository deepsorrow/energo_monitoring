package com.example.energo_monitoring.checks.ui.fragments.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.PopupWindowCompat
import androidx.databinding.Observable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.di.modules.VM_GENERAL_INSPECTION_VM
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.adapters.ProjectPhotoAdapter
import com.example.energo_monitoring.checks.ui.fragments.dialogs.ProjectPhotoPreviewDialog
import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager
import com.example.energo_monitoring.checks.ui.viewmodel.GeneralInspectionVM
import com.example.energo_monitoring.databinding.FragmentStep3GeneralInspectionBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Named

class Step3_GeneralInspectionFragment : DaggerFragment() {
    @Inject
    @Named(VM_GENERAL_INSPECTION_VM)
    lateinit var viewModel: GeneralInspectionVM
    @Inject
    lateinit var adapter: ProjectPhotoAdapter

    private var lastCreatedPathUri: Uri? = null
    private var lastCreatedPathReal: String = ""
    private lateinit var takePhotoResult: ActivityResultLauncher<Intent>
    private lateinit var pickFromGalleryResult: ActivityResultLauncher<Intent>
    private lateinit var takePermissions: ActivityResultLauncher<Intent>
    private lateinit var loadImageManager: LoadImageManager

    private lateinit var binding: FragmentStep3GeneralInspectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep3GeneralInspectionBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.projectPhotoList.adapter = adapter
        binding.projectPhotoList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        viewModel.initialize()
        if (viewModel.projectFiles?.isNotEmpty() == true) {
            binding.noFilesText.visibility = View.GONE
        }

        viewModel.currentPreviewFile.observe(viewLifecycleOwner) {
            val file = viewModel.currentPreviewFile.value
            if (file != null) {
                ProjectPhotoPreviewDialog(file).show(parentFragmentManager, "Preview")
            }
        }

        viewModel.currentLightValue.observe(viewLifecycleOwner) {
            binding.lightNoButton.isChecked = (it != null) && it.not()
            binding.lightYesButton.isChecked = (it != null) && it
        }

        viewModel.currentSanPin.observe(viewLifecycleOwner) {
            binding.sanPinNoButton.isChecked = (it != null) && it.not()
            binding.sanPinYesButton.isChecked = (it != null) && it
        }

        viewModel.organizationName.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                (requireActivity() as CheckMainActivity).setToolbarText(viewModel.organizationName.get().orEmpty())
            }
        })

        binding.addFileButton.setOnClickListener {
            val popupView = inflater.inflate(R.layout.list_add_project_file, null)
            popupView.findViewById<Button>(R.id.take_photo).setOnClickListener {
                lastCreatedPathUri = LoadImageManager.getPhotoUri(requireContext()) {
                    lastCreatedPathReal = it
                }
                LoadImageManager.takePhoto(takePhotoResult, lastCreatedPathUri)
            }
            popupView.findViewById<Button>(R.id.pick_from_gallery).setOnClickListener {
                lastCreatedPathUri = LoadImageManager.getPhotoUri(requireContext()) {
                    lastCreatedPathReal = it
                }
                loadImageManager.pickFromGallery()
            }
            popupView.findViewById<Button>(R.id.choose_file).setOnClickListener {
                Toast.makeText(context, "Выбирать файлы пока нельзя", Toast.LENGTH_LONG).show()
            }
            val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true)

            CoroutineScope(Dispatchers.Main).launch {
                delay(100)
                PopupWindowCompat.showAsDropDown(popupWindow, binding.addFileButton, 0, 0, Gravity.BOTTOM)
            }
            binding.addFileButton.animate().rotation(binding.addFileButton.rotation + 90f)
        }

        addToggleGroupListener()

        registerLaunchers()

        return binding.root
    }

    private fun addToggleGroupListener(){
        binding.lightToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.light_yes_button -> viewModel.lightIsOk = true
                    R.id.light_no_button -> viewModel.lightIsOk = false
                }
            }
        }

        binding.sanPinToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.sanPin_yes_button -> viewModel.sanPinIsOk = true
                    R.id.sanPin_no_button -> viewModel.sanPinIsOk = false
                }
            }
        }
    }

    private fun registerLaunchers(){
        takePhotoResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                viewModel.saveProjectPhoto(result, adapter, lastCreatedPathReal) {
                    binding.projectPhotoList.scrollToPosition(
                        (viewModel.projectFiles?.let { it.count() - 1 }) ?: 0
                    )
                }
            }

        pickFromGalleryResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null && result.data!!.data != null) {
                    context?.contentResolver?.openInputStream(result.data!!.data!!).use { input ->
                        val outputStream = FileOutputStream(lastCreatedPathReal)
                        outputStream.use { output ->
                            val buffer = ByteArray(4 * 1024) // buffer size
                            if (input != null) {
                                while (true) {
                                    val byteCount = input.read(buffer)
                                    if (byteCount < 0) break
                                    output.write(buffer, 0, byteCount)
                                }
                                output.flush()
                            }
                        }
                    }

                    viewModel.saveProjectPhoto(result, adapter, lastCreatedPathReal) {
                        binding.projectPhotoList.scrollToPosition(
                            (viewModel.projectFiles?.let { it.count() - 1 }) ?: 0
                        )
                    }
                }
            }

        takePermissions = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        loadImageManager.pickFromGallery()
                    } else {
                        loadImageManager.takePermission()
                    }
                }
            }
        }

        loadImageManager = LoadImageManager(requireContext(), pickFromGalleryResult, takePermissions)
    }
}