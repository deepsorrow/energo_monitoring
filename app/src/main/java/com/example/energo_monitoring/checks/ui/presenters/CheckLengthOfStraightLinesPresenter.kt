package com.example.energo_monitoring.checks.ui.presenters

import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager.Companion.getPhotoUri
import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager.Companion.takePhoto
import com.example.energo_monitoring.checks.ui.fragments.screens.Step5_CheckLengthsFragment
import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer
import com.example.energo_monitoring.checks.data.FlowTransducerLength
import com.example.energo_monitoring.checks.ui.adapters.FlowTransducerPhotoAdapter
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import android.content.Intent
import com.example.energo_monitoring.checks.ui.vm.CheckLengthViewModel
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import io.reactivex.schedulers.Schedulers
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import io.reactivex.Observable
import java.io.IOException
import java.util.ArrayList

class CheckLengthOfStraightLinesPresenter(
    private val fragment: Step5_CheckLengthsFragment,
    var devices: List<DeviceFlowTransducer?>, dataId: Int
) {
    private val results: ArrayList<FlowTransducerLength?>
    val photosAdapter: FlowTransducerPhotoAdapter
    @JvmField
    var photos: ArrayList<Bitmap>? = null
    var currentId = 0
    var takePhotoLauncher: ActivityResultLauncher<Intent>
    var model: CheckLengthViewModel? = null
    var lastCreatedPath: Uri? = null
    fun saveAndGetLengthBefore(id: Int, currentLength: String): String {
        if (!currentLength.isEmpty()) results[currentId]!!.lengthBefore = currentLength
        return results[id]!!.lengthBefore.toString()
    }

    fun saveAndGetLengthAfter(id: Int, currentLength: String): String {
        if (!currentLength.isEmpty()) results[currentId]!!.lengthAfter = currentLength
        return results[id]!!.lengthAfter.toString()
    }

    fun saveLengths(lengthBefore: String?, lengthAfter: String?) {
        results[currentId]!!.lengthBefore = lengthBefore
        results[currentId]!!.lengthAfter = lengthAfter
    }

    fun saveAndSetPhotos(id: Int) {
        results[currentId]!!.photos = photos
        val newPhotos = results[id]!!.photos
        if (newPhotos == null || newPhotos.size == 1) initPhotos() else photos = newPhotos
        photosAdapter.notifyDataSetChanged()
    }

    fun addPhoto(bitmapPhoto: Bitmap) {
        // insert new photo to end, move photoButton to begin
        photos!!.removeAt(photos!!.size - 1)
        photos!!.add(bitmapPhoto)
        photos!!.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    }

    fun onPhotoCardClicked(vh: FlowTransducerPhotoAdapter.PhotoViewHolder) {
        if (vh.layoutPosition == photos!!.size - 1) {
            lastCreatedPath = getPhotoUri(fragment.requireContext()) {
                (fragment.activity as CheckMainActivity).lastCreatedPathReal = it
            }
            takePhoto(takePhotoLauncher, lastCreatedPath)
        }
    }

    fun setViewModel(model: CheckLengthViewModel?) {
        this.model = model
    }

    fun onDeviceCardClicked(id: Int) {
        model!!.currentDeviceId.value = id
    }

    private fun initPhotos() {
        // create empty element to click it to take first image
        photos = ArrayList()
        photos!!.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    }

    fun getResult(id: Int): FlowTransducerLength? {
        return results[id]
    }

    fun insertDataToDb(context: Context?) {
        val db = ResultDataDatabase.getDatabase(context)
        Observable.just(db)
            .subscribeOn(Schedulers.io())
            .subscribe { value: ResultDataDatabase? ->
                db.resultDataDAO().insertFlowTransducerCheckLengthResults(results)
            }
    }

    init {
        photosAdapter = FlowTransducerPhotoAdapter(fragment.requireContext(), this)
        initPhotos()
        results = ArrayList()
        for (i in devices.indices) {
            results.add(FlowTransducerLength(i, devices[i], dataId))
        }
        takePhotoLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val photo = MediaStore.Images.Media.getBitmap(
                        fragment.requireActivity().contentResolver,
                        lastCreatedPath
                    )
                    results[model!!.currentDeviceId.value!!]!!.photosString += lastCreatedPath.toString() + ";"
                    addPhoto(photo)
                    photosAdapter.notifyDataSetChanged()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(fragment.requireContext(), e.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}