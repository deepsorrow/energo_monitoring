package com.example.energo_monitoring.presentation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.energo_monitoring.databinding.ActivityTakePhotoOfSchemeKnotEnergyBinding;
import com.example.energo_monitoring.data.api.ClientDataBundle;
import com.example.energo_monitoring.domain.Utils;
import com.example.energo_monitoring.presentation.presenters.utilities.LoadImageManager;
import com.example.energo_monitoring.presentation.presenters.ProjectPhotoPresenter;
import com.example.energo_monitoring.data.api.ServerService;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.presentation.presenters.utilities.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Step1_ProjectPhotoActivity extends AppCompatActivity {

    public int dataId;
    public ActivityTakePhotoOfSchemeKnotEnergyBinding binding;
    ProjectPhotoPresenter presenter;
    public Uri lastCreatedPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTakePhotoOfSchemeKnotEnergyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataId = getIntent().getIntExtra("dataId", 0);
        Utils.logProgress(this, 1, dataId);

        ProjectPhotoPresenter presenter = new ProjectPhotoPresenter(this, dataId);
        presenter.registerLaunchers(binding.photoPreview, binding.photoWasNotFoundText);

        binding.cardViewTakePhoto.setOnClickListener(v -> {
            lastCreatedPath = LoadImageManager.getPhotoUri(this);
            LoadImageManager.takePhoto(this, presenter.getTakePhotoResult(), lastCreatedPath);
        });

        binding.cardViewLoadFromGallery.setOnClickListener(v -> {
            presenter.getLoadImageManager().pickFromGallery();
        });

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), Step2_GoToPlaceActivity.class);
            intent.putExtra("dataId", dataId);
            startActivity(intent);
        });

        int selectedId = getIntent().getIntExtra("dataId", 0);
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
                presenter.insertDataToDb(getApplicationContext(), response, dataId);

                binding.loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ClientDataBundle> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Не удалось получить данные! Ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
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