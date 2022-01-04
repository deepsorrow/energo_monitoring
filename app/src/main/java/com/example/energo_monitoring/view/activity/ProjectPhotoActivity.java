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
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.energo_monitoring.databinding.ActivityTakePhotoOfSchemeKnotEnergyBinding;
import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.model.api.ClientInfo;
import com.example.energo_monitoring.model.api.ProjectDescription;
import com.example.energo_monitoring.model.db.ResultData;
import com.example.energo_monitoring.model.db.ResultDataDatabase;
import com.example.energo_monitoring.presenter.utilities.LoadImageManager;
import com.example.energo_monitoring.presenter.ProjectPhotoPresenter;
import com.example.energo_monitoring.presenter.ServerService;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.presenter.utilities.SharedPreferencesManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.lang.invoke.ConstantCallSite;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectPhotoActivity extends AppCompatActivity {

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
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            BitmapDrawable drawable = (BitmapDrawable) binding.photoPreview.getDrawable();
//            Bitmap bitmap = drawable.getBitmap();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            byte[] bb = baos.toByteArray();
//            String imageBase64 = Base64.encodeToString(bb, 0);

            Intent intent = new Intent(getApplicationContext(), GoToPlaceActivity.class);
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