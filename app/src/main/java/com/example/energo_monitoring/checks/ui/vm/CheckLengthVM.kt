package com.example.energo_monitoring.checks.ui.vm

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.databinding.ObservableField
import com.example.energo_monitoring.checks.data.CheckLengthResult
import com.example.energo_monitoring.checks.data.files.CheckLengthPhotoFile
import com.example.energo_monitoring.checks.ui.fragments.dialogs.ProjectPhotoPreviewDialog
import com.example.energo_monitoring.checks.ui.vm.base.BaseScreenVM
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

class CheckLengthVM @Inject constructor(application: Application)
    : BaseScreenVM(5, application) {

    var lengthBefore = ObservableField("")
    var lengthAfter = ObservableField("")
    var comment = ObservableField("")

    var photos = mutableListOf<Bitmap>()
    lateinit var result: CheckLengthResult

    var showPreview: ((CheckLengthPhotoFile) -> Unit)? = null
    var takePhotoAction: (() -> Unit)? = null
    var notifyPhotoChanged: (() -> Unit)? = null

    override fun initialize() {
        if (!initialized) {
            addButtonToAddPhoto()
            initialized = true
        }
    }

    fun initResult(result: CheckLengthResult) {
        this.result = result

        photos.clear()
        result.photoFiles.forEach {
            val file = File(it.path)
            val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            photos.add(bitmap)
        }
        addButtonToAddPhoto()
        notifyPhotoChanged?.let { it() }
    }

    fun lengthBeforeChanged() = saveResult {
        result.lengthBefore = lengthBefore.get().orEmpty()
    }

    fun lengthAfterChanged() = saveResult {
        result.lengthAfter = lengthAfter.get().orEmpty()
    }

    fun commentChanged() = saveResult {
        result.comment = comment.get().orEmpty()
    }

    fun addAndSavePhoto(photo: Bitmap, path: String) {
        addPhoto(photo)
        savePhoto(path)
    }

    fun onPhotoCardClicked(position: Int) {
        if (position == photos.size - 1) {
            takePhotoAction?.let { it() }
        } else {
            val file = result.photoFiles[position]
            showPreview?.let { it(file) }
        }
    }

    private fun addButtonToAddPhoto() =
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))

    private fun addPhoto(photo: Bitmap) {
        photos.removeAt(photos.size - 1)
        photos.add(photo)
        notifyPhotoChanged?.let { it() }
        addButtonToAddPhoto()
    }

    private fun savePhoto(path: String) {
        val file = CheckLengthPhotoFile()
        file.id = 103 + Random.nextInt(100000)
        file.checkLengthParentId = result.id
        file.dataId = dataId
        file.path = path
        file.title = path.substring(path.lastIndexOf("/") + 1)
        file.extension = path.substringAfterLast(".")

        result.photoFiles.add(file)
        repository.insertCheckLengthPhotoFile(file)
    }

    private fun saveResult(saveResult: () -> Unit) {
        saveResult()
        repository.insertFlowTransducerCheckLengthResults(listOf(result))
    }
}