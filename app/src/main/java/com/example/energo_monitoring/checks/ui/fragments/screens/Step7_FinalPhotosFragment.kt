package com.example.energo_monitoring.checks.ui.fragments.screens

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.energo_monitoring.R
import com.example.energo_monitoring.compose.activities.CreatingNew1Activity
import com.example.energo_monitoring.checks.data.api.ServerService
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase
import com.example.energo_monitoring.databinding.FragmentStep7FinalPhotosFragmentsBinding
import com.example.energo_monitoring.checks.ui.utils.Utils
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity
import com.example.energo_monitoring.checks.ui.adapters.PhotosRecyclerAdapter
import com.example.energo_monitoring.checks.ui.presenters.utilities.LoadImageManager
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.lang.IllegalArgumentException

class Step7_FinalPhotosFragment : Fragment() {

    private lateinit var binding: FragmentStep7FinalPhotosFragmentsBinding
    private var dataId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStep7FinalPhotosFragmentsBinding.inflate(layoutInflater)

        val activity = requireActivity() as CheckMainActivity
        val dataId = activity.dataId ?: 0

        Utils.logProgress(requireContext(), 6, dataId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.initPhotos(this, R.id.generalPhotoRecycler)
        Utils.initPhotos(this, R.id.deviceParkRecycler)
        Utils.initPhotos(this, R.id.sealsRecycler)
        Utils.initPhotos(this, R.id.otherList)
    }

    fun onComplete(view: View?) {
        val context = requireContext()

        val db = ResultDataDatabase.getDatabase(context)
        Observable.just(db)
            .doOnError { Toast.makeText(context, it.message, Toast.LENGTH_LONG).show() }
            .subscribe {
                val otherInfo = db.resultDataDAO().getOtherInfo(dataId)
                    ?: throw IllegalArgumentException("Нет данных otherInfo по dataId = $dataId")
                val photosGeneral = (binding.generalPhotoRecycler.adapter as PhotosRecyclerAdapter).photos
                val photosDevices = (binding.deviceParkRecycler.adapter as PhotosRecyclerAdapter).photos
                val photosSeals = (binding.sealsRecycler.adapter as PhotosRecyclerAdapter).photos
                otherInfo.finalPhotosGeneral = bitmapArrayToJsonArray(photosGeneral)
                otherInfo.finalPhotosDevices = bitmapArrayToJsonArray(photosDevices)
                otherInfo.finalPhotosSeals = bitmapArrayToJsonArray(photosSeals)

                val resultData = db.resultDataDAO().getData(dataId)
                    ?: throw IllegalArgumentException("Нет данных resultData по dataId = $dataId")
                resultData.otherInfo = otherInfo
                for (result in resultData.flowTransducerLengths)
                    result.photosBase64 = LoadImageManager.getBase64FromPath(context, result.photosString)
                if (resultData.project.photoPath != null && resultData.project.photoPath.isNotEmpty())
                    resultData.project.photoBase64 = LoadImageManager.getBase64FromPath(context, resultData.project.photoPath)
                ServerService.getService().sendResults(resultData)
                    .enqueue(object : Callback<Boolean?> {
                        override fun onResponse(
                            call: Call<Boolean?>,
                            response: Response<Boolean?>
                        ) {
                            if (response.body() != null && response.body() == true) {
                                Toast.makeText(context,"Завершено. Данные были успешно отправлены!",Toast.LENGTH_LONG).show()
                                val intent = Intent(context, CreatingNew1Activity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(context,"На стороне сервера произошла ошибка!", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<Boolean?>, t: Throwable) {
                            Toast.makeText(context,"Не удалось отправить данные! Ошибка: " + t.message, Toast.LENGTH_LONG).show()
                        }
                    })
            }
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