package com.example.energo_monitoring.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.Model.DeviceRecycleItem;
import com.example.energo_monitoring.Presenter.InspectionDevicePresenter;
import com.example.energo_monitoring.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InspectionDeviceAdapter extends RecyclerView.Adapter<InspectionDeviceAdapter.InspectionDeviceViewHolder> {

    private InspectionDevicePresenter presenter;
    private ArrayList<DeviceRecycleItem> devices;
    public RecyclerView recyclerView;
    private int currentPosition = 0;

    public InspectionDeviceAdapter(InspectionDevicePresenter presenter, RecyclerView recyclerView,
                                   ArrayList<DeviceRecycleItem> devices) {
        this.presenter = presenter;
        this.devices = devices;
    }

    public class InspectionDeviceViewHolder extends RecyclerView.ViewHolder{

        View view;

        public InspectionDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setData(DeviceRecycleItem device){

            TextView deviceName = view.findViewById(R.id.deviceName);
            deviceName.setText(device.getName());

            if(device.getTypeId() == 1){ // Преобразователь расхода
                List<String> items = Arrays.asList("Подающий трубопровод", "Обратный трубопровод");
                ArrayAdapter<String> adapter = new ArrayAdapter<> (presenter.getContext(),
                        R.layout.list_item, items);

                AutoCompleteTextView dropdownButton = view.findViewById(R.id.filled_exposed_dropdown);
                dropdownButton.setAdapter(adapter);

            } else{ // Вычислитель количества теплоты

                List<String> items = Arrays.asList("Отопление", "ГВС", "Пар", "Вентиляция");
                ArrayAdapter<String> adapter = new ArrayAdapter<> (presenter.getContext(),
                        R.layout.list_item, items);

                AutoCompleteTextView dropdownButton = view.findViewById(R.id.filled_exposed_dropdown);
                dropdownButton.setAdapter(adapter);

            }

        }
    }

    @NonNull
    @Override
    public InspectionDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View newView;

        if(devices.get(currentPosition).getTypeId() == 1) // Вычислитель количества теплоты
            newView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_card_temperature_counter, parent,false);
        else
            newView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_card_flow_transducer2, parent,false);

        currentPosition += 1;

        return new InspectionDeviceAdapter.InspectionDeviceViewHolder(newView);
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionDeviceViewHolder holder, int position) {
        holder.setData(devices.get(position));
        TextView countNumber = holder.view.findViewById(R.id.countNumber);

        int newPosition = position + 1;
        countNumber.setText("" + newPosition + "/" + devices.size());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
