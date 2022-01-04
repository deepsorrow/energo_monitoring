package com.example.energo_monitoring.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.energo_monitoring.R;

public class GoToPlaceActivity extends AppCompatActivity {

    public int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_to_go_to_place);

        dataId = getIntent().getIntExtra("dataId", dataId);

        Button button = findViewById(R.id.buttonContinue);
        button.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), GeneralInspectionActivity.class);
            intent.putExtra("dataId", dataId);
            startActivity(intent);
        });
    }
}