package com.example.energo_monitoring.checks.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.energo_monitoring.checks.di.modules.VM_CHECK_LENGTH_VM
import com.example.energo_monitoring.checks.ui.adapters.FlowTransducerPhotoAdapter
import com.example.energo_monitoring.checks.ui.fragments.dialogs.ProjectPhotoPreviewDialog
import com.example.energo_monitoring.checks.ui.fragments.screens.HostCheckLengthsFragment
import com.example.energo_monitoring.databinding.FragmentCheckLengthBinding
import com.example.energo_monitoring.checks.ui.vm.CheckLengthVM
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

class CheckLengthFragment : DaggerFragment() {

    private lateinit var binding: FragmentCheckLengthBinding

    @Named(VM_CHECK_LENGTH_VM)
    @Inject
    lateinit var viewModel: CheckLengthVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val hostFragment = (parentFragment as HostCheckLengthsFragment)
        binding = FragmentCheckLengthBinding.inflate(layoutInflater)

        val position = arguments?.getInt("position") ?: 0
        val result = hostFragment.viewModel.getSavedResult(position)

        viewModel.initialize()
        viewModel.initResult(result)
        binding.viewModel = viewModel

        viewModel.takePhotoAction = { hostFragment.takePhoto() }

        val adapter = FlowTransducerPhotoAdapter(viewModel)
        binding.photosList.adapter = adapter
        binding.photosList.layoutManager = GridLayoutManager(context, 2)
        viewModel.notifyPhotoChanged = { adapter.notifyDataSetChanged() }
        viewModel.showPreview = { file ->  ProjectPhotoPreviewDialog(file).show(parentFragmentManager, "Preview") }

        hostFragment.viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as HostCheckLengthsFragment).takePhotoCallback = { photo: Bitmap, path: String ->
            viewModel.addAndSavePhoto(photo, path)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            CheckLengthFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", position)
                }
            }
    }
}