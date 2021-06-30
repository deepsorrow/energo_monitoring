package com.example.energo_monitoring.View.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.View.Adapters.FinalPhotosRecyclerAdapter;

public class FinalPlacePhotosActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> takePhotoResultGeneral;
    ActivityResultLauncher<Intent> takePhotoResultDevices;
    ActivityResultLauncher<Intent> takePhotoResultSeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_place_photos);

        initPhotos(R.id.generalPhotoRecycler, takePhotoResultGeneral);
        initPhotos(R.id.deviceParkRecycler, takePhotoResultDevices);
        initPhotos(R.id.sealsRecycler, takePhotoResultSeals);
    }

    public void initPhotos(int recyclerId, ActivityResultLauncher<Intent> launcher) {
        RecyclerView recyclerView = findViewById(recyclerId);
        FinalPhotosRecyclerAdapter recyclerAdapter =
                new FinalPhotosRecyclerAdapter(getApplicationContext());

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap thumbnail = (Bitmap) result.getData().getExtras().get("data");
                        recyclerAdapter.addPhoto(thumbnail);
                        recyclerAdapter.notifyDataSetChanged();
                    }
                });

        recyclerAdapter.setPhotoLauncher(launcher);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,
                        false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}