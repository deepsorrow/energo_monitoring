package com.example.energo_monitoring.checks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.energo_monitoring.databinding.ProjectFileListItemBinding
import javax.inject.Inject

class ProjectPhotoAdapter @Inject constructor() : RecyclerView.Adapter<ProjectPhotoAdapter.ViewHolder>() {
    class ViewHolder(binding: ProjectFileListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ProjectFileListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}