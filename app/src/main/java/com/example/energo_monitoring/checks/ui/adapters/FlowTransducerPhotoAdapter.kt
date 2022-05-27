package com.example.energo_monitoring.checks.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.graphics.Bitmap
import com.example.energo_monitoring.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.energo_monitoring.checks.ui.vm.CheckLengthVM
import javax.inject.Inject

class FlowTransducerPhotoAdapter constructor(var viewModel: CheckLengthVM) :
    RecyclerView.Adapter<FlowTransducerPhotoAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun loadImageFromBitmap(bitmap: Bitmap?) {
            val photoImageView = itemView.findViewById<ImageView>(R.id.photoImageView)
            photoImageView.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_flow_transducers_photo_card, parent, false)

        val vh = PhotoViewHolder(view)
        val cardView = view.findViewById<CardView>(R.id.photoCard)
        cardView.setOnClickListener { viewModel.onPhotoCardClicked(vh.layoutPosition) }
        return vh
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.loadImageFromBitmap(viewModel.photos[position])
        if (position + 1 != itemCount) {
            holder.itemView.findViewById<ImageView>(R.id.photoImageView).visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = viewModel.photos.size
}