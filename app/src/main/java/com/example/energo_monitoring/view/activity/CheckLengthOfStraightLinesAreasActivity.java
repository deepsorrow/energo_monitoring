package com.example.energo_monitoring.view.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.model.FlowTransducerCheckLengthResult;
import com.example.energo_monitoring.model.TemperatureCounterCharacteristicsParameter;
import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.presenter.CheckLengthOfStraightLinesPresenter;
import com.example.energo_monitoring.presenter.utilities.SharedPreferencesManager;
import com.example.energo_monitoring.view.adapters.DeviceFlowTransducerListAdapter;
import com.example.energo_monitoring.view.adapters.FlowTransducerPhotoAdapter;
import com.example.energo_monitoring.databinding.ActivityCheckLengthOfStraightLineAreasBinding;
import com.example.energo_monitoring.view.viewmodel.CheckLengthViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CheckLengthOfStraightLinesAreasActivity extends AppCompatActivity {

    ActivityCheckLengthOfStraightLineAreasBinding binding;
    CheckLengthOfStraightLinesPresenter presenter;
    private CheckLengthViewModel model;
    DeviceFlowTransducerListAdapter deviceAdapter;
    public int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckLengthOfStraightLineAreasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataId = getIntent().getIntExtra("dataId", dataId);

        ClientDataBundle dataBundle = SharedPreferencesManager.getClientDataBundle(this);
        if(dataBundle.getDeviceFlowTransducers().isEmpty()){
            Intent intent = new Intent(getApplicationContext(), TemperatureCounterCharacteristicsActivity.class);
            intent.putExtra("dataId", dataId);
            startActivity(intent);
            finish();
            return;
        }

        presenter = new CheckLengthOfStraightLinesPresenter(this, dataBundle, dataId);

        model = new ViewModelProvider(this).get(CheckLengthViewModel.class);

        final Observer<Integer> currentIdObserver = integer -> {

            String currentLengthBefore = binding.lengthBefore.getText().toString();
            String newLengthBefore = presenter.saveAndGetLengthBefore(integer, currentLengthBefore);
            String currentLengthAfter = binding.lengthAfter.getText().toString();
            String newLengthAfter = presenter.saveAndGetLengthAfter(integer, currentLengthAfter);

            if(!currentLengthBefore.isEmpty() && !currentLengthAfter.isEmpty() && presenter.photos.size() > 1) // complete
                presenter.getResult(presenter.currentId).setIcon(R.drawable.ic_baseline_check_circle_24);
            else if(!currentLengthBefore.isEmpty() || !currentLengthAfter.isEmpty() || presenter.photos.size() > 1) // touched
                presenter.getResult(presenter.currentId).setIcon(R.drawable.ic_baseline_incomplete_circle_24);
            else // untouched
                presenter.getResult(presenter.currentId).setIcon(R.drawable.ic_baseline_trip_origin_24);

            deviceAdapter.notifyItemChanged(presenter.currentId);

            binding.lengthBefore.setText(newLengthBefore);

            binding.lengthAfter.setText(newLengthAfter);

            presenter.saveAndSetPhotos(integer);

            presenter.currentId = integer;
        };

        model.getCurrentDeviceId().setValue(0);
        model.getCurrentDeviceId().observe(this, currentIdObserver);
        presenter.setViewModel(model);

        initLists();

        binding.buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), TemperatureCounterCharacteristicsActivity.class);
            intent.putExtra("dataId", dataId);

            // save current photos
            String currentLengthBefore = binding.lengthBefore.getText().toString();
            String currentLengthAfter = binding.lengthAfter.getText().toString();
            presenter.saveLengths(currentLengthBefore, currentLengthAfter);
            if(model.getCurrentDeviceId().getValue() != null)
                presenter.saveAndSetPhotos(model.getCurrentDeviceId().getValue());

            presenter.insertDataToDb(this);

            startActivity(intent);
        });
    }

    public void initLists(){
        deviceAdapter = new DeviceFlowTransducerListAdapter(presenter);

        binding.deviceList.setLayoutManager(new LinearLayoutManager(this));
        binding.deviceList.setAdapter(deviceAdapter);

        binding.photosList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.photosList.setAdapter(presenter.getPhotosAdapter());
    }
}