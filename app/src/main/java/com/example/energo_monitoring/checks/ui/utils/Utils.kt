package com.example.energo_monitoring.checks.ui.utils

import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.checks.data.db.OtherInfo
import com.example.energo_monitoring.checks.ui.adapters.PhotosRecyclerAdapter
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import androidx.recyclerview.widget.RecyclerView
import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.energo_monitoring.R
import io.reactivex.disposables.Disposables
import java.io.IOException

object Utils {
    /**
     * Записать прогресс для восстанавления из главной страницы при необходимости
     * @param dataId - идентификатор данных для отправки на сервер
     */
    fun logProgress(context: Context?, currentScreen: Int, dataId: Int) {
        val db = ResultDataDatabase.getDatabase(context)
        var otherInfo = db.resultDataDAO().getOtherInfo(dataId)
        if (otherInfo == null) otherInfo = OtherInfo(dataId)
        otherInfo.completedScreens = Math.max(otherInfo.completedScreens, currentScreen)
        otherInfo.localVersion += 1
        otherInfo.currentScreen = currentScreen
        db.resultDataDAO().insertOtherInfo(otherInfo)
    }

    fun initPhotos(
        fragment: Fragment,
        recyclerId: Int,
        recyclerAdapter: PhotosRecyclerAdapter,
        columns: Int
    ) {
        val context = fragment.requireContext()
        val activity = fragment.requireActivity() as CheckMainActivity
        val recyclerView: RecyclerView = fragment.requireView().findViewById(recyclerId)
        val launcher = fragment.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val photo = MediaStore.Images.Media.getBitmap(
                        activity.contentResolver,
                        activity.lastCreatedPath
                    )
                    recyclerAdapter.addPhoto(photo)
                    recyclerAdapter.notifyDataSetChanged()
                } catch (e: IOException) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
        recyclerAdapter.setPhotoLauncher(launcher)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = recyclerAdapter
        if (columns == 1) {
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            val gridLayoutManager = GridLayoutManager(context, 2)
            recyclerView.layoutManager = gridLayoutManager
        }
    }

    fun initPhotos(fragment: Fragment, recyclerId: Int, listItem: Int, columns: Int) {
        val recyclerAdapter = PhotosRecyclerAdapter(fragment.requireContext(), listItem)
        initPhotos(fragment, recyclerId, recyclerAdapter, columns)
    }

    fun initPhotos(fragment: Fragment, recyclerId: Int) {
        val recyclerAdapter = PhotosRecyclerAdapter(
            fragment.requireContext(),
            R.layout.list_item_final_photos_photo_card
        )
        initPhotos(fragment, recyclerId, recyclerAdapter, 1)
    }
}