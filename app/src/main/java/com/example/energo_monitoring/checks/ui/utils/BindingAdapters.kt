package com.example.energo_monitoring.checks.ui.utils

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:filePath")
fun ImageView.loadImage(filePath: String) {
    val bitmap = BitmapFactory.decodeFile(filePath)
    setImageBitmap(bitmap)
}