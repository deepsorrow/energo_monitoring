package com.example.energo_monitoring.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.energo_monitoring.data.api.ClientDataBundle;
import com.example.energo_monitoring.presentation.presenters.TemperatureCounterCharacteristicsPresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.presentation.adapters.TemperatureCounterValuesMainListAdapter;
import com.example.energo_monitoring.presentation.adapters.TemperatureCounterValuesTabsAdapter;
import com.example.energo_monitoring.databinding.ActivityTemperatureCounterCharacteristicsBinding;
import com.example.energo_monitoring.presentation.viewmodel.TemperatureCounterViewModel;
import com.google.gson.Gson;


public class TemperatureCounterCharacteristicsActivity extends AppCompatActivity {

    TemperatureCounterCharacteristicsPresenter presenter;
    TemperatureCounterValuesTabsAdapter adapterTabs;
    TemperatureCounterValuesMainListAdapter adapterParameters;
    public ActivityTemperatureCounterCharacteristicsBinding binding;
    TemperatureCounterViewModel model;
    public int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTemperatureCounterCharacteristicsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataId = getIntent().getIntExtra("dataId", dataId);

        model = new ViewModelProvider(this).get(TemperatureCounterViewModel.class);

        SharedPreferences mPrefs = getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("currentClientDataBundle", "");
        ClientDataBundle clientDataBundle = gson.fromJson(json, ClientDataBundle.class);

        if(clientDataBundle.getDeviceCounters().size() == 0) {
            Intent intent = new Intent(getApplicationContext(), FinalPlacePhotosActivity.class);
            intent.putExtra("dataId", dataId);
            startActivity(intent);
            finish();
            return;
        }

        presenter = new TemperatureCounterCharacteristicsPresenter(this, model);

        initTabs();
        initMainList();
        presenter.setAdapters(adapterTabs, adapterParameters);

        binding.buttonContinue.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), FinalPlacePhotosActivity.class);
            intent.putExtra("dataId", dataId);

            presenter.insertDataToDb(dataId, binding.temperatureCounterComment.getText().toString());
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