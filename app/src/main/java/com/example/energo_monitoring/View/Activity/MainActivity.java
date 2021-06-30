package com.example.energo_monitoring.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.energo_monitoring.Model.ClientRecycleItem;
import com.example.energo_monitoring.Presenter.MainPresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.View.Adapters.ClientsRecyclerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPresenter mainPresenter = new MainPresenter(this);

        ClientRecycleItem client1 = new ClientRecycleItem("12345123",
                "г. Новокузнецк, ул. Покрышкина 15/2", "ООО Гребцы на галере",
                "Начальник смены, Левин Андрей Михайлович", "+7(495)581-12-22", "test@mail.ru");
        ClientRecycleItem client2 = new ClientRecycleItem("89017261",
                "г. Новосибирск, ул. ДОЗ 20а стр.4", "ООО Рабочая сила",
                "Руководитель ТТО, Власов Илья Викторович", "+7(499)112-51-62", "rrrah62ga@gmail.ru");
        ClientRecycleItem client3 = new ClientRecycleItem("5151251",
                "г. Кемерово, ул. ДОЗ 20а стр.4", "Детский сад №27",
                "Руководитель ТТО, Власов Илья Викторович", "+7(499)112-51-62", "ffah62ga@gmail.ru");
        ClientRecycleItem client4 = new ClientRecycleItem("617777",
                "г. Казань, ул. ДОЗ 20а стр.4", "МБНОУ СОШ №200",
                "Руководитель ТТО, Власов Илья Викторович", "+7(499)112-51-62", "r2231ah62ga@gmail.ru");
        ClientRecycleItem client5 = new ClientRecycleItem("9281827",
                "г. Москва, ул. ДОЗ 20а стр.4", "МФТИ",
                "Руководитель ТТО, Власов Илья Викторович", "+7(499)112-51-62", "rrtestestga@mail.ru");
        ArrayList<ClientRecycleItem> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewClients);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ClientsRecyclerAdapter(mainPresenter, recyclerView, clients));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }



}