package com.example.energo_monitoring.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.energo_monitoring.presenter.TemperatureCounterCharacteristicsPresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.view.adapters.TemperatureCounterValuesMainListAdapter;
import com.example.energo_monitoring.view.adapters.TemperatureCounterValuesTabsAdapter;
import com.example.energo_monitoring.databinding.ActivityTemperatureCounterCharacteristicsBinding;
import com.example.energo_monitoring.view.viewmodel.TemperatureCounterViewModel;


public class TemperatureCounterCharacteristicsActivity extends AppCompatActivity {

    TemperatureCounterCharacteristicsPresenter presenter;
    TemperatureCounterValuesTabsAdapter adapterTabs;
    TemperatureCounterValuesMainListAdapter adapterParameters;
    ActivityTemperatureCounterCharacteristicsBinding binding;
    TemperatureCounterViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTemperatureCounterCharacteristicsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(TemperatureCounterViewModel.class);
//        final Observer<Integer> currentId = integer -> {
//
//        };
//
//        model.getCurrentDeviceId().observe(this, );

        presenter = new TemperatureCounterCharacteristicsPresenter(this, model);

        initTabs();
        initMainList();
        presenter.setAdapters(adapterTabs, adapterParameters);

        binding.buttonContinue.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FinalPlacePhotosActivity.class);
            startActivity(intent);
        });
    }

    public void initTabs(){
        adapterTabs = new TemperatureCounterValuesTabsAdapter(presenter);

        binding.temperatureCounterTabs.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.HORIZONTAL, false));
        binding.temperatureCounterTabs.setAdapter(adapterTabs);
    }

    public void initMainList(){
        adapterParameters = new TemperatureCounterValuesMainListAdapter(presenter);

        RecyclerView recyclerView = findViewById(R.id.temperatureCounterCharacteristicsRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapterParameters);
    }
}