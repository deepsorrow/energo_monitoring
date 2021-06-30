package com.example.energo_monitoring.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.Presenter.MainPresenter;
import com.example.energo_monitoring.R;
import com.example.energo_monitoring.Model.ClientRecycleItem;
import com.example.energo_monitoring.View.Activity.ProjectPhotoActivity;

import java.util.ArrayList;

public class ClientsRecyclerAdapter extends RecyclerView.Adapter<ClientsRecyclerAdapter.ClientsRecycleViewHolder> {
    Context context;

    public ArrayList<ClientRecycleItem> clients;
    public RecyclerView recyclerView;
    public MainPresenter mainPresenter;

    public ClientsRecyclerAdapter(MainPresenter mainPresenter, RecyclerView recyclerView, ArrayList<ClientRecycleItem> clients) {
        this.mainPresenter = mainPresenter;
        this.recyclerView = recyclerView;
        this.clients      = clients;
    }

    public class ClientsRecycleViewHolder extends RecyclerView.ViewHolder{
        private View view;

        public ClientsRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setData(ClientRecycleItem client){
            TextView agreementNumberValue = view.findViewById(R.id.agreementNumberValue);
            agreementNumberValue.setText(client.getAgreementNumber());

            TextView knotAddressValue = view.findViewById(R.id.knotAddressValue);
            knotAddressValue.setText(client.getKnotAddress());

            TextView clientNameValue = view.findViewById(R.id.clientNameValue);
            clientNameValue.setText(client.getName());

            TextView representativeValue = view.findViewById(R.id.representativeValue);
            representativeValue.setText(client.getRepresentativeName());

            TextView phoneValue = view.findViewById(R.id.phoneValue);
            phoneValue.setText(client.getPhoneNumber());

            TextView emailValue = view.findViewById(R.id.emailValue);
            emailValue.setText(client.getEmail());
        }

    }

    @NonNull
    @Override
    public ClientsRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_card, parent,false);
        view.setOnClickListener((v) -> {
            Intent intent = new Intent(mainPresenter.getContext(), ProjectPhotoActivity.class);

            TextView agreementNumberValue = view.findViewById(R.id.agreementNumberValue);
            intent.putExtra("agreementNumber", agreementNumberValue.getText());

            mainPresenter.getContext().startActivity(intent);
        });

        return new ClientsRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsRecycleViewHolder holder, int position) {
        holder.setData(clients.get(position));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

}
