package com.example.energy_monitoring.checks.ui.vm

import android.app.Application
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.example.energy_monitoring.checks.data.files.FinalPhotoFile
import com.example.energy_monitoring.checks.data.files.FinalPhotoType
import com.example.energy_monitoring.checks.ui.vm.base.BaseScreenVM
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

class FinalPhotosVM @Inject constructor(application: Application)
    : BaseScreenVM(5, application) {

    var generalPhotoFiles = mutableListOf<FinalPhotoFile>()
    var devicePhotoFiles = mutableListOf<FinalPhotoFile>()
    var sealsPhotoFiles = mutableListOf<FinalPhotoFile>()
    var otherPhotoFiles = mutableListOf<FinalPhotoFile>()

    var takePhotoAction: ((FinalPhotoType) -> Unit)? = null
    var showPreview: ((FinalPhotoFile) -> Unit)? = null

    override fun initialize() {
        super.initialize()

        initialized && return

        repository.getFinalPhotoFiles(dataId, FinalPhotoType.General)?.forEach {
            it.bitmap = getBitmapFromPath(it.path)
            generalPhotoFiles.add(it)
        }

        repository.getFinalPhotoFiles(dataId, FinalPhotoType.DevicePark)?.forEach {
            it.bitmap = getBitmapFromPath(it.path)
            devicePhotoFiles.add(it)
        }

        repository.getFinalPhotoFiles(dataId, FinalPhotoType.Seals)?.forEach {
            it.bitmap = getBitmapFromPath(it.path)
            sealsPhotoFiles.add(it)
        }

        repository.getFinalPhotoFiles(dataId, FinalPhotoType.Other)?.forEach {
            it.bitmap = getBitmapFromPath(it.path)
            otherPhotoFiles.add(it)
        }

        initialized = true
    }

    fun savePhoto(path: String, type: FinalPhotoType) {
        val finalPhotoFile = FinalPhotoFile()
        finalPhotoFile.id = 104 + Random.nextInt(100000)
        finalPhotoFile.dataId = dataId
        finalPhotoFile.path = path
        finalPhotoFile.type = type
        finalPhotoFile.bitmap = getBitmapFromPath(path)

        repository.insertFinalPhotoFile(finalPhotoFile)

        when (type) {
            FinalPhotoType.General -> generalPhotoFiles.add(finalPhotoFile)
            FinalPhotoType.DevicePark -> devicePhotoFiles.add(finalPhotoFile)
            FinalPhotoType.Seals -> sealsPhotoFiles.add(finalPhotoFile)
            FinalPhotoType.Other -> otherPhotoFiles.add(finalPhotoFile)
        }
    }

    fun onPhotoCardClicked(type: FinalPhotoType, position: Int) {
        val photoFiles = getPhotoFiles(type)

        if (position == photoFiles.size) {
            takePhotoAction?.let { it(type) }
        } else {
            val file = photoFiles[position]
            showPreview?.let { it(file) }
        }
    }

    fun getPhotos(type: FinalPhotoType) = getPhotoFiles(type).map { it.bitmap }

    private fun getPhotoFiles(type: FinalPhotoType) =
        when (type) {
            FinalPhotoType.General -> generalPhotoFiles
            FinalPhotoType.DevicePark -> devicePhotoFiles
            FinalPhotoType.Seals -> sealsPhotoFiles
            FinalPhotoType.Other -> otherPhotoFiles
        }

    private fun getBitmapFromPath(path: String): Bitmap {
        val file = File(path)
        val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
        return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
}