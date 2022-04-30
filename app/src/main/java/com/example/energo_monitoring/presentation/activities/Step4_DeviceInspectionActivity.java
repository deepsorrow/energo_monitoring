package com.example.energo_monitoring.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.energo_monitoring.databinding.ActivityInspectionDeviceBinding;
import com.example.energo_monitoring.data.api.ClientDataBundle;
import com.example.energo_monitoring.data.api.DeviceInfo;
import com.example.energo_monitoring.domain.Utils;
import com.example.energo_monitoring.presentation.presenters.DeviceInspectionInterface;
import com.example.energo_monitoring.presentation.presenters.DeviceInspectionPresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.presentation.presenters.utilities.SharedPreferencesManager;
import com.example.energo_monitoring.presentation.adapters.DeviceInspectionTabsAdapter;
import com.example.energo_monitoring.presentation.viewmodel.InspectionDeviceViewModel;

import java.util.ArrayList;

public class Step4_DeviceInspectionActivity extends AppCompatActivity implements DeviceInspectionInterface {

    public int dataId;
    ActivityInspectionDeviceBinding binding;
    DeviceInspectionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInspectionDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataId = getIntent().getIntExtra("dataId", 0);
        Utils.logProgress(this, 4, dataId);

        ClientDataBundle clientDataBundle = SharedPreferencesManager.getClientDataBundle(this);
        ArrayList<DeviceInfo> devices = new ArrayList<>();
        devices.addAll(clientDataBundle.getDeviceTemperatureCounters());
        devices.addAll(clientDataBundle.getDeviceFlowTransducers());
        devices.addAll(clientDataBundle.getDeviceTemperatureTransducers());
        devices.addAll(clientDataBundle.getDevicePressureTransducers());

        if(devices.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(),
                    Step5_CheckLengthOfStraightLinesAreasActivity.class);
            intent.putExtra("dataId", dataId);

            startActivity(intent);
            finish();
            return;
        }

        presenter = new DeviceInspectionPresenter(this, devices);
        presenter.model = new ViewModelProvider(this).get(InspectionDeviceViewModel.class);

        binding.devicesTabs.setAdapter(new DeviceInspectionTabsAdapter(presenter));
        binding.devicesTabs.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL, false));

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putInt("deviceId", 0);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentDevices, presenter.fragments.get(0))
                    .commit();
        }

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            presenter.insertDataToDb(dataId);

            Intent intent = new Intent(getApplicationContext(),
                    Step5_CheckLengthOfStraightLinesAreasActivity.class);
            intent.putExtra("dataId", dataId);

            startActivity(intent);
        });
    }

    @Override
    public DeviceInfo getDevice(int id) {
        return presenter.devices.get(id);
    }

    @Override
    public TextWatcher getLastCheckDateListener(EditText date, DeviceInfo device) {
        return presenter.getLastCheckDateTextWatcher(date, device);
    }
}