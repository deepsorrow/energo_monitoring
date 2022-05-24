package com.example.energo_monitoring.checks.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.energo_monitoring.checks.di.modules.VM_GENERAL_INSPECTION_VM
import com.example.energo_monitoring.checks.ui.vm.GeneralInspectionVM
import com.example.energo_monitoring.databinding.ProjectFileListItemBinding
import javax.inject.Inject
import javax.inject.Named

class ProjectPhotoAdapter @Inject constructor(
    @Named(VM_GENERAL_INSPECTION_VM) val viewModel: GeneralInspectionVM
) : RecyclerView.Adapter<ProjectPhotoAdapter.ViewHolder>() {
    class ViewHolder(val binding: ProjectFileListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProjectFileListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = viewModel.projectFiles?.get(position)
        if (file != null) {
            holder.binding.file = file
            holder.binding.fileLayout.setOnClickListener {
                viewModel.currentPreviewFile.value = file
            }
        }
        if (position != itemCount)
            holder.binding.rightDivider.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int = viewModel.projectFiles?.count() ?: 0
}