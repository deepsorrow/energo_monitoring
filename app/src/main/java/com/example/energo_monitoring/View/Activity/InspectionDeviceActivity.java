package com.example.energo_monitoring.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.energo_monitoring.Model.DeviceFlowTransducer;
import com.example.energo_monitoring.Model.DeviceRecycleItem;
import com.example.energo_monitoring.Model.DeviceTemperatureCounter;
import com.example.energo_monitoring.Presenter.InspectionDevicePresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.View.Adapters.InspectionDeviceAdapter;

import java.util.ArrayList;

public class InspectionDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_device);

        InspectionDevicePresenter presenter = new InspectionDevicePresenter(getApplicationContext());

        ArrayList<DeviceRecycleItem> devices = new ArrayList<>();
        devices.add(new DeviceFlowTransducer("ПРЭМ", 2));
        devices.add(new DeviceTemperatureCounter("ВКТ-9", 1));
        devices.add(new DeviceFlowTransducer("ПРЭМ", 2));
        devices.add(new DeviceTemperatureCounter("ВКТ-9", 1));
        devices.add(new DeviceFlowTransducer("ПРЭМ", 2));
        devices.add(new DeviceTemperatureCounter("ВКТ-9", 1));

        RecyclerView recyclerView = findViewById(R.id.listOfDevices);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new InspectionDeviceAdapter(presenter, recyclerView, devices));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), FinalPlacePhotosActivity.class);
            startActivity(intent);
        });
    }
    
}