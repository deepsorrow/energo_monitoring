package com.example.energy_monitoring.checks.ui.adapters

import android.content.Context
import com.example.energy_monitoring.checks.ui.utils.LoadImageManager.Companion.getPhotoUri
import com.example.energy_monitoring.checks.ui.utils.LoadImageManager.Companion.takePhoto
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import com.example.energy_monitoring.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.energy_monitoring.checks.data.files.FinalPhotoType
import com.example.energy_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energy_monitoring.checks.ui.vm.FinalPhotosVM

class FinalPhotosAdapter(
    var context: Context,
    val viewModel: FinalPhotosVM,
    val type: FinalPhotoType
) : RecyclerView.Adapter<FinalPhotosAdapter.PhotoViewHolder>() {

    var photos: MutableList<Bitmap?> = viewModel.getPhotos(type).toMutableList()
    var launcher: ActivityResultLauncher<Intent>? = null

    fun addPhoto(bitmapPhoto: Bitmap) {
        // insert new photo to end, move photoButton to begin
        photos.removeAt(photos.size - 1)
        photos.add(bitmapPhoto)
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun loadImageFromBitmap(bitmap: Bitmap?) {
            val photoImageView = itemView.findViewById<ImageView>(R.id.photoImageView)
            photoImageView.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_final_photos_photo_card, parent, false)
        val vh = PhotoViewHolder(view)
        val cardView: CardView = view.findViewById(R.id.photoCard)
        cardView.setOnClickListener { viewModel.onPhotoCardClicked(type, vh.layoutPosition) }
        return vh
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.loadImageFromBitmap(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    init {

        // create empty element to click it to take first image
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    }
}