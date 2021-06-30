package com.example.energo_monitoring.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.energo_monitoring.R;

public class InspectionGeneralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_general);

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), InspectionDeviceActivity.class);
            startActivity(intent);
        });
    }
}