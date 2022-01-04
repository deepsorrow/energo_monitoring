package com.example.energo_monitoring.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.databinding.ActivityInspectionGeneralBinding;
import com.example.energo_monitoring.model.db.ResultData;
import com.example.energo_monitoring.model.db.ResultDataDatabase;
import com.example.energo_monitoring.presenter.GeneralInspectionPresenter;

public class GeneralInspectionActivity extends AppCompatActivity {

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

        Button buttonContinue = findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            Intent intent = new Intent(getApplicationContext(), DeviceInspectionActivity.class);
            intent.putExtra("dataId", dataId);

            presenter.insertDataToDb(dataId, binding.checkBoxLight.isChecked(),
                    binding.checkBoxSanPin.isChecked(), binding.commentEditText.getText().toString());

            startActivity(intent);
        });
    }
}