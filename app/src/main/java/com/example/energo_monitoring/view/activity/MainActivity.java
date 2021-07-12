package com.example.energo_monitoring.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.energo_monitoring.model.api.ClientInfo;
import com.example.energo_monitoring.presenter.MainPresenter;
import com.example.energo_monitoring.presenter.ServerService;
import com.example.energo_monitoring.presenter.utilities.SharedPreferencesManager;
import com.example.energo_monitoring.ui.login.LoginActivity;
import com.example.energo_monitoring.view.adapters.ClientsRecyclerAdapter;
import com.example.energo_monitoring.databinding.ActivityMainBinding;
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

        binding.swipeRefreshLayout.setOnRefreshListener(() -> updateAvailableClients());

        updateAvailableClients();
    }

    public void updateAvailableClients(){
        ServerService.getService().getAvailableClientInfo().enqueue(new Callback<List<ClientInfo>>() {
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

    public void logout(View view) {
        SharedPreferencesManager.clearUsername(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}