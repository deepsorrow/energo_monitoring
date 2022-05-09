package com.example.energo_monitoring.checks.ui.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.energo_monitoring.checks.data.ProjectFile
import com.example.energo_monitoring.databinding.ProjectPhotoPreviewBinding

class ProjectPhotoPreviewDialog(val file: ProjectFile) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val binding = ProjectPhotoPreviewBinding.inflate(requireActivity().layoutInflater)
            binding.file = file

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}