package com.example.energo_monitoring.View.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.energo_monitoring.Presenter.LoadImageManager;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.View.Activity.GoToPlaceActivity;

public class ProjectPhotoActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> takePhotoResult;
    ActivityResultLauncher<Intent> getPhotoFromGalleryResult;
    ActivityResultLauncher<Intent> takePermissions;
    LoadImageManager loadImageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_of_scheme_knot_energy);

        ImageView photoPreview = findViewById(R.id.photoPreview);

        takePhotoResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap thumbnail = (Bitmap) result.getData().getExtras().get("data");
                            photoPreview.setImageBitmap(thumbnail);
                    }
                });

        getPhotoFromGalleryResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();

                        TextView photoWasNotFoundText = findViewById(R.id.photoWasNotFoundText);
                        photoWasNotFoundText.setText("Фото проекта успешно загружено!");

                        photoPreview.setImageURI(selectedImage);
                    }
                });

        takePermissions = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                            if(Environment.isExternalStorageManager()){
                                loadImageManager.pickFromGallery();
                            } else{
                                loadImageManager.takePermission();
                            }
                        }
                    }
                });

        loadImageManager = new LoadImageManager(this, takePhotoResult,
                getPhotoFromGalleryResult, takePermissions);

        CardView takePhotoButton = findViewById(R.id.cardViewTakePhoto);
        CardView takeFromGallery = findViewById(R.id.cardViewLoadFromGallery);

        takePhotoButton.setOnClickListener(v -> {
            LoadImageManager.takePhoto(takePhotoResult);
        });

        takeFromGallery.setOnClickListener(v -> {
            loadImageManager.pickFromGallery();
        });

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), GoToPlaceActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        loadImageManager.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

}