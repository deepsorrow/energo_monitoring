package com.example.energy_monitoring.checks.ui.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.energy_monitoring.checks.data.files.base.BaseFile
import com.example.energy_monitoring.databinding.DialogProjectPhotoPreviewBinding

class ProjectPhotoPreviewDialog(val file: BaseFile) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val binding = DialogProjectPhotoPreviewBinding.inflate(requireActivity().layoutInflater)
            binding.file = file

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}