package com.example.energo_monitoring.checks.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.energo_monitoring.checks.data.api.ClientDataBundle
import com.example.energo_monitoring.checks.data.api.ServerService
import com.example.energo_monitoring.databinding.FragmentStep1ProjectPhotoBinding
import com.example.energo_monitoring.checks.ui.utils.Utils
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.presenters.CheckMainPresenter
import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager
import com.example.energo_monitoring.checks.ui.presenters.utilities.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Step1_ProjectPhotoFragment : Fragment() {

    lateinit var binding: FragmentStep1ProjectPhotoBinding
    private var dataId: Int = 0
    lateinit var lastCreatedPath: Uri
    var presenter: CheckMainPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep1ProjectPhotoBinding.inflate(layoutInflater, container, false)

        val activity = requireActivity() as CheckMainActivity
        dataId = activity.dataId
        Utils.logProgress(requireContext(), 1, dataId)

        val presenter =
            CheckMainPresenter(
                this,
                dataId
            )
        presenter.registerLaunchers(binding.photoPreview, binding.photoWasNotFoundText)

        binding.cardViewTakePhoto.setOnClickListener {
            //lastCreatedPath = LoadImageManager.getPhotoUri(requireContext()) {}
            LoadImageManager.takePhoto(presenter.takePhotoResult, lastCreatedPath)
        }

        binding.cardViewLoadFromGallery.setOnClickListener {
            presenter.loadImageManager.pickFromGallery()
        }

        ServerService.getService().getDetailedClientBundle(dataId)
            .enqueue(object : Callback<ClientDataBundle> {
                override fun onResponse(
                    call: Call<ClientDataBundle>,
                    response: Response<ClientDataBundle>
                ) {
                    SharedPreferencesManager.saveClientDataBundle(
                        binding.root.context,
                        response.body()
                    )
                    if (response.body() == null){
                        Toast.makeText(requireContext(), "Произошла ошибка! Код ошибки: 101.", Toast.LENGTH_LONG).show()
                        return
                    }
                    if (response.body()!!.project.photoBase64.length > 10) {
                        binding.photoPreview.setImageBitmap(presenter.getBitmapFromBase64(response.body()))
                        binding.photoWasNotFoundText.text = ""
                        binding.cardViewLoadFromGallery.visibility = View.GONE
                        binding.cardViewTakePhoto.visibility = View.GONE
                    }
                    presenter.insertDataToDb(requireContext(), response, dataId)
                    binding.loading.visibility = View.GONE
                }

                override fun onFailure(call: Call<ClientDataBundle>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось получить данные! Ошибка: " + t.message, Toast.LENGTH_LONG
                    ).show()
                    binding.loading.visibility = View.GONE
                }
            })

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter?.loadImageManager?.onRequestPermissionResult(requestCode, permissions, grantResults)
    }
}