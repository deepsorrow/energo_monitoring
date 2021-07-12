package com.example.energo_monitoring.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.model.api.DeviceInfo;
import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.presenter.DeviceInspectionPresenter;
import com.example.energo_monitoring.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceInspectionAdapter extends RecyclerView.Adapter<DeviceInspectionAdapter.InspectionDeviceViewHolder> {

    private DeviceInspectionPresenter presenter;
    //private ClientDataBundle clientDataBundle;
    private ArrayList<DeviceInfo> devices;
    public RecyclerView recyclerView;
    private int currentPosition = 0;

//    public InspectionDeviceAdapter(InspectionDevicePresenter presenter, RecyclerView recyclerView,
//                                   ClientDataBundle clientDataBundle) {
//        this.presenter = presenter;
//        this.clientDataBundle = clientDataBundle;
//    }


    public DeviceInspectionAdapter(DeviceInspectionPresenter presenter, ArrayList<DeviceInfo> devices, RecyclerView recyclerView) {
        this.presenter = presenter;
        this.devices = devices;
        this.recyclerView = recyclerView;
    }

    public class InspectionDeviceViewHolder extends RecyclerView.ViewHolder{

        TextView deviceName;

        public InspectionDeviceViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.deviceName);
            EditText lastCheckDate = itemView.findViewById(R.id.lastCheckDate);
            lastCheckDate.addTextChangedListener(presenter.getLastCheckDateTextWatcher(lastCheckDate));
        }

        public void setData(DeviceInfo device){
            deviceName.setText(device.getName() + " №" + device.getDeviceNumber());
            switch (device.getTypeId()) {
                case 1:
                    initSpinner(Arrays.asList("Отопление", "ГВС", "Пар", "Вентиляция"), R.id.unitSystemSpinner);
                    break;
                case 2:
                    initSpinner(Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                    initSpinner(Arrays.asList("Взлет", "Теплоком", "Термотроник"), R.id.manufacturerSpinner);
                    EditText diameter = itemView.findViewById(R.id.diameter);

                    DeviceFlowTransducer deviceFlowTransducer = (DeviceFlowTransducer) device;
                    diameter.setText(deviceFlowTransducer.getDiameter());
                    break;
                case 3:
                    initSpinner(Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                    break;
                case 4:
                    initSpinner(Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                    initSpinner(Arrays.asList("НПК ВИП", "Тепловодохран"), R.id.manufacturerSpinner);
                    break;
            }
        }

        public void initSpinner(List<String> values, int spinnerId){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(presenter.getContext(),
                    R.layout.list_item_spinner, values);

            AutoCompleteTextView dropdownButton = itemView.findViewById(spinnerId);
            if(dropdownButton != null)
                dropdownButton.setAdapter(adapter);
        }
    }

    @NonNull
    @Override
    public InspectionDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View newView;

        //switch(clientDataBundle.get(currentPosition).getTypeId()) {
        switch(devices.get(currentPosition).getTypeId()) {
            case 1: // Вычислитель количества теплоты
                newView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.device_card_temperature_counter, parent, false);
                break;
            case 2:
                newView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.device_card_flow_transducer2, parent, false);
                break;
            case 3:
                newView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.device_card_temperature_transducer, parent, false);
                break;
            case 4:
                newView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.device_card_pressure_transducer, parent, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + devices.get(currentPosition).getTypeId());
        }

        currentPosition += 1;

        return new DeviceInspectionAdapter.InspectionDeviceViewHolder(newView);
    }

    @Override
    public void onBindViewHolder(@NonNull InspectionDeviceViewHolder holder, int position) {

//        boolean takeFrom1 = position <= clientDataBundle.getDeviceTemperatureCounters().size();
//        boolean takeFrom2 = position <= (clientDataBundle.getDeviceFlowTransducers().size()
//                + clientDataBundle.getDeviceTemperatureCounters().size());
//        boolean takeFrom3 = position <= (clientDataBundle.getDeviceFlowTransducers().size()
//                + clientDataBundle.getDeviceTemperatureCounters().size()
//                + clientDataBundle.getDeviceTemperatureTransducers().size());
//        boolean takeFrom4 = position <= (clientDataBundle.getDeviceFlowTransducers().size()
//                + clientDataBundle.getDeviceTemperatureCounters().size()
//                + clientDataBundle.getDeviceTemperatureTransducers().size()
//                + clientDataBundle.getDevicePressureTransducers().size());
//
//        if(takeFrom1)

//        switch(devices.get(position).getTypeId()){
//            case 1: holder.setData1(devices.get(position)); break;
//            case 2: holder.setData2(devices.get(position)); break;
//        }
        holder.setData(devices.get(position));
//        TextView countNumber = holder.itemView.findViewById(R.id.countNumber);

//        int newPosition = position + 1;
//        countNumber.setText("" + newPosition + "/" + devices.size());
    }

    @Override
    public int getItemCount() {
//        return clientDataBundle.getDevicePressureTransducers().size()
//                + clientDataBundle.getDeviceTemperatureCounters().size()
//                + clientDataBundle.getDeviceCounters().size()
//                + clientDataBundle.getDeviceTemperatureTransducers().size()
//                + clientDataBundle.getDeviceFlowTransducers().size();
        return devices.size();
    }
}
