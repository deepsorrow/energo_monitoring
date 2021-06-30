package com.example.energo_monitoring.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.View.Activity.InspectionGeneralActivity;

public class GoToPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_to_go_to_place);

        Button button = findViewById(R.id.buttonContinue);
        button.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), InspectionGeneralActivity.class);
            startActivity(intent);
        });
    }
}