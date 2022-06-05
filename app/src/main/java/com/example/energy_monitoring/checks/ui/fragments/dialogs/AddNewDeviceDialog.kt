package com.example.energy_monitoring.checks.ui.fragments.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.energy_monitoring.R
import com.example.energy_monitoring.checks.di.modules.VM_ADD_NEW_DEVICE_VM
import com.example.energy_monitoring.checks.ui.utils.DeviceUtils
import com.example.energy_monitoring.checks.ui.vm.AddNewDeviceVM
import com.example.energy_monitoring.compose.DeviceType
import com.example.energy_monitoring.databinding.DialogAddNewDeviceBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Named

class AddNewDeviceDialog(
    val deviceType: DeviceType = DeviceType.Any,
    val onDeviceAddedCallback: () -> Unit
): DialogFragment() {

    lateinit var binding: DialogAddNewDeviceBinding

    @Inject
    @Named(VM_ADD_NEW_DEVICE_VM)
    lateinit var viewModel: AddNewDeviceVM

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = DialogAddNewDeviceBinding.inflate(requireActivity().layoutInflater)
            binding.viewModel = viewModel

            DeviceUtils.initSpinner(binding.root, viewModel.deviceTypes, R.id.deviceType)
            setDeviceType(deviceType.title)

            binding.deviceType.addTextChangedListener { newType -> onDeviceTypeChanged("$newType") }

            binding.confirmButton.setOnClickListener {
                viewModel.saveDevice()
                onDeviceAddedCallback()
                dismiss()
            }

            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun onDeviceTypeChanged(newType: String) {
        when (newType) {
            DeviceType.TemperatureCounter.title -> {
                binding.additionalFieldLayout1.visibility = View.GONE
                binding.additionalFieldLayout2.visibility = View.GONE
            }
            DeviceType.FlowTransducer.title -> {
                binding.additionalFieldLayout1.visibility = View.VISIBLE
                binding.additionalFieldLayout1.hint = "Диаметр"
                binding.additionalFieldLayout2.visibility = View.VISIBLE
                binding.additionalFieldLayout2.hint = "Вес импульса"
            }
            DeviceType.TemperatureTransducer.title -> {
                binding.additionalFieldLayout1.visibility = View.VISIBLE
                binding.additionalFieldLayout1.hint = "Длина"
                binding.additionalFieldLayout2.visibility = View.GONE
            }
            DeviceType.PressureTransducer.title -> {
                binding.additionalFieldLayout1.visibility = View.VISIBLE
                binding.additionalFieldLayout1.hint = "Диапазон датчика"
                binding.additionalFieldLayout2.visibility = View.GONE
            }
        }
    }

    private fun setDeviceType(type: String) {
        if (type.isEmpty()) {
            viewModel.deviceType.set("")
            binding.deviceType.focusable = View.FOCUSABLE
        } else {
            viewModel.deviceType.set(type)
            binding.deviceType.focusable = View.NOT_FOCUSABLE
            binding.deviceTypeLayout.endIconDrawable = null
        }
    }
}