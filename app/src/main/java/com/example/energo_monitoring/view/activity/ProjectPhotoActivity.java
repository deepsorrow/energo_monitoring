package com.example.energo_monitoring.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.energo_monitoring.databinding.ActivityTakePhotoOfSchemeKnotEnergyBinding;
import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.presenter.utilities.LoadImageManager;
import com.example.energo_monitoring.presenter.ProjectPhotoPresenter;
import com.example.energo_monitoring.presenter.ServerService;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.presenter.utilities.SharedPreferencesManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.lang.invoke.ConstantCallSite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectPhotoActivity extends AppCompatActivity {

    ActivityTakePhotoOfSchemeKnotEnergyBinding binding;
    ProjectPhotoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTakePhotoOfSchemeKnotEnergyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProjectPhotoPresenter presenter = new ProjectPhotoPresenter(this);
        presenter.registerLaunchers(binding.photoPreview, binding.photoWasNotFoundText);

        binding.cardViewTakePhoto.setOnClickListener(v -> {
            LoadImageManager.takePhoto(presenter.getTakePhotoResult());
        });

        binding.cardViewLoadFromGallery.setOnClickListener(v -> {
            presenter.getLoadImageManager().pickFromGallery();
        });

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), GoToPlaceActivity.class);
            startActivity(intent);
        });

        Long selectedId = getIntent().getLongExtra("id", 0);
        ServerService.getService().getDetailedClientBundle(selectedId).enqueue(new Callback<ClientDataBundle>() {
            @Override
            public void onResponse(Call<ClientDataBundle> call, Response<ClientDataBundle> response) {
                SharedPreferencesManager.saveClientDataBundle(binding.getRoot().getContext(),
                        response.body());
                if(response.body().getProject().getPhotoBase64().length() > 10) {
                    binding.photoPreview.setImageBitmap(presenter.getBitmapFromBase64(response.body()));
                    binding.photoWasNotFoundText.setText("Фото проекта");

                    binding.cardViewLoadFromGallery.setVisibility(View.GONE);
                    binding.cardViewTakePhoto.setVisibility(View.GONE);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(binding.constaintLayout);
                    constraintSet.connect(R.id.photoPreview, ConstraintSet.BOTTOM, R.id.buttonContinue, ConstraintSet.TOP, 0);
                    constraintSet.applyTo(binding.constaintLayout);
                }
                binding.loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ClientDataBundle> call, Throwable t) {
                Snackbar.make(binding.photoPreview, "Не удалось получить данные! Ошибка: "
                        + t.getMessage(), 3).show();
                binding.loading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.getLoadImageManager()
                .onRequestPermissionResult(requestCode, permissions, grantResults);
    }

}