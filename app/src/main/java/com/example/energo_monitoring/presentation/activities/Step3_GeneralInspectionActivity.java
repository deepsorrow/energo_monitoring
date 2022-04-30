package com.example.energo_monitoring.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.databinding.ActivityInspectionGeneralBinding;
import com.example.energo_monitoring.domain.Utils;
import com.example.energo_monitoring.presentation.presenters.GeneralInspectionPresenter;

public class Step3_GeneralInspectionActivity extends AppCompatActivity {

    public int dataId;
    ActivityInspectionGeneralBinding binding;
    GeneralInspectionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInspectionGeneralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new GeneralInspectionPresenter(this);

        dataId = getIntent().getIntExtra("dataId", 0);
        Utils.logProgress(this, 3, dataId);

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), Step4_DeviceInspectionActivity.class);
            intent.putExtra("dataId", dataId);

            presenter.insertDataToDb(dataId, binding.checkBoxLight.isChecked(),
                    binding.checkBoxSanPin.isChecked(), binding.commentEditText.getText().toString());

            startActivity(intent);
        });
    }
}