package com.example.energy_monitoring.checks.ui.fragments.screens

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.energy_monitoring.R
import com.example.energy_monitoring.checks.data.files.FinalPhotoType
import com.example.energy_monitoring.checks.data.files.FinalPhotoType.*
import com.example.energy_monitoring.checks.di.DATA_ID
import com.example.energy_monitoring.checks.di.modules.VM_FINAL_PHOTOS_VM
import com.example.energy_monitoring.databinding.FragmentStep5FinalPhotosFragmentsBinding
import com.example.energy_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energy_monitoring.checks.ui.adapters.FinalPhotosAdapter
import com.example.energy_monitoring.checks.ui.fragments.dialogs.ProjectPhotoPreviewDialog
import com.example.energy_monitoring.checks.ui.utils.LoadImageManager
import com.example.energy_monitoring.checks.ui.vm.FinalPhotosVM
import dagger.android.support.DaggerFragment
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class FinalPhotosFragment : DaggerFragment() {

    @Inject
    @Named(VM_FINAL_PHOTOS_VM)
    lateinit var viewModel: FinalPhotosVM

    lateinit var activity: CheckMainActivity
    private lateinit var binding: FragmentStep5FinalPhotosFragmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep5FinalPhotosFragmentsBinding.inflate(layoutInflater)
        activity = requireActivity() as CheckMainActivity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initialize()
        val savePhoto = { path: String, type: FinalPhotoType ->
            viewModel.savePhoto(path, type)
        }

        val GeneralPhotosAdapter = initPhotos(R.id.generalPhotoRecycler, General, savePhoto)
        val DeviceParkAdapter = initPhotos(R.id.deviceParkRecycler, DevicePark, savePhoto)
        val SealsAdapter = initPhotos(R.id.sealsRecycler, Seals, savePhoto)
        val OtherAdapter = initPhotos(R.id.otherList, Other, savePhoto)

        viewModel.takePhotoAction = { type: FinalPhotoType ->
            activity.lastCreatedPath = LoadImageManager.getPhotoUri(requireContext(), viewModel.dataId) {
                activity.lastCreatedPathReal = it
            }
            val launcher = when (type) {
                General -> GeneralPhotosAdapter.launcher
                DevicePark -> DeviceParkAdapter.launcher
                Seals -> SealsAdapter.launcher
                Other -> OtherAdapter.launcher
            }
            launcher?.let { LoadImageManager.takePhoto(launcher, activity.lastCreatedPath) }
        }

        viewModel.showPreview = { file ->  ProjectPhotoPreviewDialog(file).show(parentFragmentManager, "Preview") }
    }

    private fun initPhotos(recyclerId: Int, type: FinalPhotoType, savePhoto: (String, FinalPhotoType) -> Unit): FinalPhotosAdapter {
        val recyclerAdapter = FinalPhotosAdapter(requireContext(), viewModel, type)

        recyclerAdapter.launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val photo = MediaStore.Images.Media.getBitmap(activity.contentResolver, activity.lastCreatedPath)
                    recyclerAdapter.addPhoto(photo)
                    recyclerAdapter.notifyDataSetChanged()
                    savePhoto(activity.lastCreatedPathReal, type)
                } catch (e: IOException) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
        }

        val recyclerView = requireView().findViewById<RecyclerView>(recyclerId)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return recyclerAdapter
    }

    private fun bitmapArrayToJsonArray(photos: ArrayList<Bitmap>): String {
        var photosBase64 = ""
        // i = 1 because first photo is image button
        for (i in 0 until photos.size - 1) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            photos[i].compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
            photosBase64 += "$encoded;"
        }
        if (photosBase64.isNotEmpty()) photosBase64.substring(0, photosBase64.length - 1)
        return photosBase64
    }

}