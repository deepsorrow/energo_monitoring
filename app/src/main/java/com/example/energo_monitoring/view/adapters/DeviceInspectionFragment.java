package com.example.energo_monitoring.view.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.databinding.DeviceCardFlowTransducer2Binding;
import com.example.energo_monitoring.databinding.DeviceCardPressureTransducerBinding;
import com.example.energo_monitoring.databinding.DeviceCardTemperatureCounterBinding;
import com.example.energo_monitoring.databinding.DeviceCardTemperatureTransducerBinding;
import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.model.DevicePressureTransducer;
import com.example.energo_monitoring.model.DeviceTemperatureCounter;
import com.example.energo_monitoring.model.DeviceTemperatureTransducer;
import com.example.energo_monitoring.model.api.DeviceInfo;
import com.example.energo_monitoring.presenter.DeviceInspectionInterface;
import com.example.energo_monitoring.presenter.DeviceInspectionPresenter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceInspectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceInspectionFragment extends Fragment {

    private int deviceId;
    private DeviceInfo device;

    public DeviceInspectionFragment() {
        // Required empty public constructor
    }

    public static DeviceInspectionFragment newInstance(int deviceId) {
        DeviceInspectionFragment fragment = new DeviceInspectionFragment();
        Bundle args = new Bundle();
        args.putInt("deviceId", deviceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceId = getArguments().getInt("deviceId", 0);
            device   = ((DeviceInspectionInterface)getActivity()).getDevice(deviceId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView;

        EditText lastCheckDate;
        switch(device.getTypeId()) {
            case 1:
                newView = inflater.inflate(R.layout.device_card_temperature_counter, container, false);
                DeviceCardTemperatureCounterBinding binding1 = DeviceCardTemperatureCounterBinding.bind(newView);
                binding1.setDevice((DeviceTemperatureCounter) device);
                lastCheckDate = binding1.lastCheckDate;

                initSpinner(newView, Arrays.asList("Отопление", "ГВС", "Пар", "Вентиляция"), R.id.unitSystemSpinner);
                break;
            case 2:
                newView = inflater.inflate(R.layout.device_card_flow_transducer2, container, false);
                DeviceCardFlowTransducer2Binding binding2 = DeviceCardFlowTransducer2Binding.bind(newView);
                binding2.setDevice((DeviceFlowTransducer) device);
                lastCheckDate = binding2.lastCheckDate;

                initSpinner(newView, Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                initSpinner(newView, Arrays.asList("Взлет", "Теплоком", "Термотроник"), R.id.manufacturerSpinner);
                break;
            case 3:
                newView = inflater.inflate(R.layout.device_card_temperature_transducer, container, false);
                DeviceCardTemperatureTransducerBinding binding3 = DeviceCardTemperatureTransducerBinding.bind(newView);
                binding3.setDevice((DeviceTemperatureTransducer) device);
                lastCheckDate = binding3.lastCheckDate;

                initSpinner(newView, Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                break;
            case 4:
                newView = inflater.inflate(R.layout.device_card_pressure_transducer, container, false);
                DeviceCardPressureTransducerBinding binding4 = DeviceCardPressureTransducerBinding.bind(newView);
                binding4.setDevice((DevicePressureTransducer) device);
                lastCheckDate = binding4.lastCheckDate;

                initSpinner(newView, Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                initSpinner(newView, Arrays.asList("НПК ВИП", "Тепловодохран"), R.id.manufacturerSpinner);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + device.getTypeId());
        }

        TextWatcher listener = ((DeviceInspectionInterface)getActivity()).getLastCheckDateListener(lastCheckDate);
        lastCheckDate.addTextChangedListener(listener);

        return newView;
    }

    public void initSpinner(View view, List<String> values, int spinnerId){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.list_item_spinner, values);

        AutoCompleteTextView dropdownButton = view.findViewById(spinnerId);
        if(dropdownButton != null)
            dropdownButton.setAdapter(adapter);
    }
}