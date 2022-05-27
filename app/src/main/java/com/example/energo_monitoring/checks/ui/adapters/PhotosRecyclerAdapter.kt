package com.example.energo_monitoring.checks.ui.adapters

import android.content.Context
import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager.Companion.getPhotoUri
import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager.Companion.takePhoto
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import com.example.energo_monitoring.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import java.util.ArrayList

class PhotosRecyclerAdapter(var context: Context, var listItem: Int) :
    RecyclerView.Adapter<PhotosRecyclerAdapter.PhotoViewHolder>() {
    var photos: ArrayList<Bitmap> = ArrayList()
    var takePhotoLauncher: ActivityResultLauncher<Intent>? = null
    fun addPhoto(bitmapPhoto: Bitmap) {
        // insert new photo to end, move photoButton to begin
        photos.removeAt(photos.size - 1)
        photos.add(bitmapPhoto)
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    }

    fun setPhotoLauncher(launcher: ActivityResultLauncher<Intent>?) {
        takePhotoLauncher = launcher
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun loadImageFromBitmap(bitmap: Bitmap?) {
            val photoImageView = itemView.findViewById<ImageView>(R.id.photoImageView)
            photoImageView.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(listItem, parent, false)
        val vh = PhotoViewHolder(view)
        val cardView: CardView = view.findViewById(R.id.photoCard)
        cardView.setOnClickListener { v: View? ->
            if (vh.layoutPosition == photos.size - 1) {
                (context as CheckMainActivity).lastCreatedPath = getPhotoUri(context) {
                    (context as CheckMainActivity).lastCreatedPathReal = it
                }
                takePhoto(takePhotoLauncher!!, (context as CheckMainActivity).lastCreatedPath)
            }
        }
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