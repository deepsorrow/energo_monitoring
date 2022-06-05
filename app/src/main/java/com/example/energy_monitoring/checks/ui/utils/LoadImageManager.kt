package com.example.energy_monitoring.checks.ui.utils

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder

class LoadImageManager(
    private val context: Context,
    private val getPhotoFromGalleryResult: ActivityResultLauncher<Intent>,
    private val takePermissions: ActivityResultLauncher<Intent>
) {
    private val isPermissionGranted: Boolean
        get() = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val readExternalStorage = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            readExternalStorage == PackageManager.PERMISSION_GRANTED
        }

    fun pickFromGallery() {
        if (isPermissionGranted) {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            galleryIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            getPhotoFromGalleryResult.launch(galleryIntent)
        } else {
            takePermission()
        }
    }

    fun takePermission() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                takePermissions.launch(intent)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                takePermissions.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                101
            )
        }
    }

    fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty()) {
            if (requestCode == 101) {
                val readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (readExternalStorage) {
                    val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
                    galleryIntent.type = "image/*"
                    galleryIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    getPhotoFromGalleryResult.launch(galleryIntent)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun takePhoto(launcher: ActivityResultLauncher<Intent>, path: Uri?) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, path)
            launcher.launch(cameraIntent)
        }

        @JvmStatic
        fun getPhotoUri(context: Context, dataId: Int, saveRealPath: (String) -> Unit): Uri {
            var file: File? = null
            try {
                val folder = Utils.getDataIdFolder(context, dataId)
                file = File.createTempFile("photo", ".png", folder)
                saveRealPath(file.canonicalPath)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
            }
            return FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider", file!!
            )
        }

        fun getBase64FromPath(context: Context, paths: String): String {
            val result = StringBuilder()
            for (path in paths.split(";").toTypedArray()) {
                if (path.isEmpty()) continue
                var bitmap: Bitmap? = null
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        Uri.parse(path)
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val byteArrayOutputStream = ByteArrayOutputStream()
                if (bitmap == null) {
                    Toast.makeText(
                        context,
                        "Произошла ошибка! Bitmap равен null!",
                        Toast.LENGTH_LONG
                    ).show()
                    return ""
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
                result.append(encoded).append(";")
            }
            if (result.isNotEmpty()) result.substring(0, result.length - 1)
            return result.toString()
        }
    }
}