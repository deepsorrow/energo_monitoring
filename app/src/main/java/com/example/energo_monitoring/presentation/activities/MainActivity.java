package com.example.energo_monitoring.presentation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.energo_monitoring.databinding.ActivityMainBinding;
import com.example.energo_monitoring.data.api.ClientInfo;
import com.example.energo_monitoring.presentation.presenters.MainPresenter;
import com.example.energo_monitoring.data.api.ServerService;
import com.example.energo_monitoring.presentation.presenters.utilities.SharedPreferencesManager;
import com.example.energo_monitoring.presentation.login.LoginActivity;
import com.example.energo_monitoring.presentation.adapters.ClientsRecyclerAdapter;
import com.example.feature_create_new_data.presentation.activities.CreatingNew1Activity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ClientsRecyclerAdapter clientsRecyclerAdapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.inspectorName.setText(SharedPreferencesManager.getUsername(this));

        MainPresenter mainPresenter = new MainPresenter(this);

        clientsRecyclerAdapter = new ClientsRecyclerAdapter(mainPresenter, binding.clientsList);
        binding.clientsList.setHasFixedSize(true);
        binding.clientsList.setAdapter(clientsRecyclerAdapter);
        binding.clientsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        binding.swipeRefreshLayout.setRefreshing(true);
        binding.swipeRefreshLayout.setOnRefreshListener(this::updateAvailableClients);

        binding.createNew.setOnClickListener(v -> {
            startActivity(new Intent(this, CreatingNew1Activity.class));
        });

        updateAvailableClients();
    }

    public void updateAvailableClients(){
        ServerService.getService().getAvailableClientInfo(
                SharedPreferencesManager.getUserId(this)).enqueue(new Callback<List<ClientInfo>>() {
            @Override
            public void onResponse(Call<List<ClientInfo>> call, Response<List<ClientInfo>> response) {
                ArrayList<ClientInfo> clients = new ArrayList<>();
                for(ClientInfo clientInfo : response.body()){
                    clients.add(clientInfo);
                }

                clientsRecyclerAdapter.setClients(clients);
                clientsRecyclerAdapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<ClientInfo>> call, Throwable t) {
                Snackbar.make(binding.clientsList, "Не удалось получить данные! Ошибка: "
                        + t.getMessage(), Snackbar.LENGTH_LONG).show();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        updateAvailableClients();
    }

    public void logout(View view) {
        SharedPreferencesManager.clearUsername(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}