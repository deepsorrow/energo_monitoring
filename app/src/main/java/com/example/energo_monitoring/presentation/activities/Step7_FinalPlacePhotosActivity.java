package com.example.energo_monitoring.presentation.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.data.db.OtherInfo;
import com.example.energo_monitoring.data.db.ResultData;
import com.example.energo_monitoring.databinding.ActivityFinalPlacePhotosBinding;
import com.example.energo_monitoring.data.FlowTransducerCheckLengthResult;
import com.example.energo_monitoring.data.db.ResultDataDatabase;
import com.example.energo_monitoring.data.api.ServerService;
import com.example.energo_monitoring.domain.Utils;
import com.example.energo_monitoring.presentation.presenters.utilities.LoadImageManager;
import com.example.energo_monitoring.presentation.adapters.FinalPhotosRecyclerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Step7_FinalPlacePhotosActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> takePhotoResultGeneral;
    ActivityResultLauncher<Intent> takePhotoResultDevices;
    ActivityResultLauncher<Intent> takePhotoResultSeals;

    ActivityFinalPlacePhotosBinding binding;

    public Uri lastCreatedPath;

    public int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinalPlacePhotosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataId = getIntent().getIntExtra("dataId", dataId);
        Utils.logProgress(this, 7, dataId);

        initPhotos(R.id.generalPhotoRecycler, takePhotoResultGeneral);
        initPhotos(R.id.deviceParkRecycler, takePhotoResultDevices);
        initPhotos(R.id.sealsRecycler, takePhotoResultSeals);
    }

    public void initPhotos(int recyclerId, ActivityResultLauncher<Intent> launcher) {
        RecyclerView recyclerView = findViewById(recyclerId);
        FinalPhotosRecyclerAdapter recyclerAdapter =
                new FinalPhotosRecyclerAdapter(this);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                    lastCreatedPath);

                            recyclerAdapter.addPhoto(photo);
                            recyclerAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

        recyclerAdapter.setPhotoLauncher(launcher);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL,
                        false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void onComplete(View view) {

        ResultDataDatabase db = ResultDataDatabase.getDatabase(this);
        Observable.just(db)
                .subscribe((value) -> {
                    OtherInfo otherInfo = db.resultDataDAO().getOtherInfo(dataId);

                    ArrayList<Bitmap> photosGeneral = ((FinalPhotosRecyclerAdapter) binding.generalPhotoRecycler.getAdapter()).photos;
                    ArrayList<Bitmap> photosDevices = ((FinalPhotosRecyclerAdapter) binding.deviceParkRecycler.getAdapter()).photos;
                    ArrayList<Bitmap> photosSeals = ((FinalPhotosRecyclerAdapter) binding.sealsRecycler.getAdapter()).photos;
                    otherInfo.finalPhotosGeneral = bitmapArrayToJsonArray(photosGeneral);
                    otherInfo.finalPhotosDevices = bitmapArrayToJsonArray(photosDevices);
                    otherInfo.finalPhotosSeals = bitmapArrayToJsonArray(photosSeals);
                    //db.resultDataDAO().insertOtherInfo(otherInfo);

                    ResultData resultData = db.resultDataDAO().getData(dataId);
                    resultData.otherInfo = otherInfo;
                    for(FlowTransducerCheckLengthResult result : resultData.flowTransducerCheckLengthResults)
                        result.photosBase64 = LoadImageManager.getBase64FromPath(this, result.photosString);

                    if(resultData.project.photoPath != null && !resultData.project.photoPath.isEmpty())
                        resultData.project.setPhotoBase64(LoadImageManager.getBase64FromPath(this, resultData.project.photoPath));

                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.buttonContinue.setEnabled(false);
                    ServerService.getService().sendResults(resultData).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body() != null && response.body() == true) {
                                Toast.makeText(getApplicationContext(),
                                        "Завершено. Данные были успешно отправлены!",
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(), TestMainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "На стороне сервера произошла ошибка!",
                                        Toast.LENGTH_LONG).show();
                            }
                            binding.progressBar.setVisibility(View.GONE);
                            binding.buttonContinue.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    "Не удалось отправить данные! Ошибка: " + t.getMessage(),
                                    Toast.LENGTH_LONG).show();

                            binding.progressBar.setVisibility(View.GONE);
                            binding.buttonContinue.setEnabled(true);
                        }
                    });


                });


        //Toast.makeText(this, "Завершено. Отправка данных на сайт еще не реализована," +
        //        " т.к. нет шаблона акта.", Toast.LENGTH_LONG).show();
    }

    private String bitmapArrayToJsonArray(ArrayList<Bitmap> photos) {
        String photosBase64 = "";
        // i = 1 because first photo is image button
        for (int i = 0; i < photos.size() - 1; ++i) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photos.get(i).compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            photosBase64 += encoded + ";";
        }
        // delete ';' from the end
        if(!photosBase64.isEmpty())
            photosBase64.substring(0, photosBase64.length() - 1);

        return photosBase64;
    }
}