package com.example.energo_monitoring.view;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.databinding.DeviceCardFlowTransducerBinding;
import com.example.energo_monitoring.databinding.DeviceCardPressureTransducerBinding;
import com.example.energo_monitoring.databinding.DeviceCardTemperatureCounterBinding;
import com.example.energo_monitoring.databinding.DeviceCardTemperatureTransducerBinding;
import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.model.DevicePressureTransducer;
import com.example.energo_monitoring.model.DeviceTemperatureCounter;
import com.example.energo_monitoring.model.DeviceTemperatureTransducer;
import com.example.energo_monitoring.model.api.DeviceInfo;
import com.example.energo_monitoring.presenter.DeviceInspectionInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceInspectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceInspectionFragment extends Fragment {

    private int deviceId;
    public DeviceInfo device;

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
                DeviceTemperatureCounter device1 = (DeviceTemperatureCounter) device;

                newView = inflater.inflate(R.layout.device_card_temperature_counter, container, false);
                DeviceCardTemperatureCounterBinding binding1 = (DeviceCardTemperatureCounterBinding)
                        DeviceCardTemperatureCounterBinding.bind(newView);
                binding1.setDevice(device1);
                lastCheckDate = binding1.lastCheckDate;

                initSpinner(newView, Arrays.asList("Отопление", "ГВС", "Пар", "Вентиляция"),
                        R.id.unitSystemSpinner);
                binding1.unitSystemSpinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device1.setUnitSystem(s.toString());
                    }
                });
                binding1.modification.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device1.setModification(s.toString());
                    }
                });
                binding1.interval.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device1.setInterval(s.toString());
                    }
                });
                binding1.comment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device1.setComment(s.toString());
                    }
                });
                break;
            case 2:
                DeviceFlowTransducer device2 = (DeviceFlowTransducer) device;

                newView = inflater.inflate(R.layout.device_card_flow_transducer, container, false);
                DeviceCardFlowTransducerBinding binding2 = DeviceCardFlowTransducerBinding.bind(newView);
                binding2.setDevice(device2);
                lastCheckDate = binding2.lastCheckDate;

                String correctDiameter = device2.getDiameter();
                binding2.diameter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device2.setDiameter(s.toString());
                        setMatchListener(binding2.diameter, binding2.diameterLayout, correctDiameter);
                    }
                });

                String correctImpulseWeight = device2.getImpulseWeight();
                binding2.impulseWeight.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device2.setImpulseWeight(s.toString());
                        setMatchListener(binding2.impulseWeight, binding2.impulseWeightLayout, correctImpulseWeight);
                    }
                });

                initSpinner(newView, Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                initSpinner(newView, Arrays.asList("Взлет", "Теплоком", "Термотроник"), R.id.manufacturerSpinner);

                binding2.installationPlaceSpinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device2.setInstallationPlace(s.toString());
                    }
                });
                binding2.manufacturerSpinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device2.setManufacturer(s.toString());
                    }
                });
                binding2.values.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device2.setValues(s.toString());
                    }
                });
                binding2.comment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device2.setComment(s.toString());
                    }
                });
                break;
            case 3:
                DeviceTemperatureTransducer device3 = (DeviceTemperatureTransducer) device;

                newView = inflater.inflate(R.layout.device_card_temperature_transducer, container, false);
                DeviceCardTemperatureTransducerBinding binding3 = DeviceCardTemperatureTransducerBinding.bind(newView);
                binding3.setDevice(device3);
                lastCheckDate = binding3.lastCheckDate;

                String correctLength = device3.getLength();
                binding3.length.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device3.setLength(s.toString());
                        setMatchListener(binding3.length, binding3.lengthLayout, correctLength);
                    }
                });

                initSpinner(newView, Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                binding3.installationPlaceSpinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device3.setInstallationPlace(s.toString());
                    }
                });

                binding3.values.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device3.setValues(s.toString());
                    }
                });

                binding3.comment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device3.setComment(s.toString());
                    }
                });

                break;
            case 4:
                DevicePressureTransducer device4 = (DevicePressureTransducer) device;

                newView = inflater.inflate(R.layout.device_card_pressure_transducer, container, false);
                DeviceCardPressureTransducerBinding binding4 = DeviceCardPressureTransducerBinding.bind(newView);
                binding4.setDevice(device4);
                lastCheckDate = binding4.lastCheckDate;

                String correctSensorRange = device4.getSensorRange();
                binding4.sensorRange.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override
                    public void afterTextChanged(Editable s) {
                        device4.setSensorRange(s.toString());
                        setMatchListener(binding4.sensorRange, binding4.sensorRangeLayout, correctSensorRange);
                    }
                });

                initSpinner(newView, Arrays.asList("Подающий трубопровод", "Обратный трубопровод"), R.id.installationPlaceSpinner);
                initSpinner(newView, Arrays.asList("НПК ВИП", "Тепловодохран"), R.id.manufacturerSpinner);

                binding4.installationPlaceSpinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device4.setInstallationPlace(s.toString());
                    }
                });

                binding4.manufacturerSpinner.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device4.setManufacturer(s.toString());
                    }
                });

                binding4.values.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device4.setValues(s.toString());
                    }
                });

                binding4.comment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        device4.setComment(s.toString());
                    }
                });
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + device.getTypeId());
        }

        setDeviceNameNumberMatchListener(newView, device);

        TextWatcher listener = ((DeviceInspectionInterface)getActivity()).getLastCheckDateListener(lastCheckDate, device);
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

    private void setDeviceNameNumberMatchListener(View deviceView, DeviceInfo device){
        TextInputEditText deviceName     = deviceView.findViewById(R.id.deviceName);
        TextInputLayout deviceNameLayout = deviceView.findViewById(R.id.deviceNameLayout);
        String correctName = device.getDeviceName();
        deviceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                device.setDeviceName(s.toString());
                setMatchListener(deviceName, deviceNameLayout, correctName);
            }
        });

        TextInputEditText deviceNumber     = deviceView.findViewById(R.id.deviceNumber);
        TextInputLayout deviceNumberLayout = deviceView.findViewById(R.id.deviceNumberLayout);
        String correctDeviceNumber = device.getDeviceNumber();
        deviceNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                device.setDeviceNumber(s.toString());
                setMatchListener(deviceNumber, deviceNumberLayout, correctDeviceNumber);
            }
        });
    }

    private void setMatchListener(TextInputEditText textInputField, TextInputLayout layout, String correctValue){
        boolean match = textInputField.getText().toString().equals(correctValue);
        //layout.setError("Значение отличается от " + correctValue);
        if (!match && correctValue != null && !correctValue.isEmpty())
            layout.setError("По проекту должно быть " + correctValue + "!");
        else
            layout.setErrorEnabled(false);
    }
}