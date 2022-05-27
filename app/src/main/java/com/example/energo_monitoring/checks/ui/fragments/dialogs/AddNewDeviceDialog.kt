package com.example.energo_monitoring.checks.ui.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.energo_monitoring.R
import com.example.energo_monitoring.checks.ui.utils.DeviceUtils
import com.example.energo_monitoring.checks.ui.vm.AddNewDeviceVM
import com.example.energo_monitoring.databinding.DialogAddNewDeviceBinding

class AddNewDeviceDialog(val deviceType: Int = -1): DialogFragment() {

    val viewModel: AddNewDeviceVM by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogAddNewDeviceBinding.inflate(requireActivity().layoutInflater)
            binding.viewModel = viewModel
            binding.confirmButton.setOnClickListener {
                dismiss()
            }

            DeviceUtils.initSpinner(binding.root, viewModel.deviceTypes, R.id.deviceType)

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}