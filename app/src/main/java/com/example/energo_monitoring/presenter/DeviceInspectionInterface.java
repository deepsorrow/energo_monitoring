package com.example.energo_monitoring.presenter;

import android.text.TextWatcher;
import android.widget.EditText;

import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.model.DevicePressureTransducer;
import com.example.energo_monitoring.model.DeviceTemperatureCounter;
import com.example.energo_monitoring.model.DeviceTemperatureTransducer;
import com.example.energo_monitoring.model.api.DeviceInfo;

public interface DeviceInspectionInterface {
//    DeviceFlowTransducer getDeviceFlowTransducer(int id);
//    DevicePressureTransducer getDevicePressureTransducer(int id);
//    DeviceTemperatureCounter getDeviceTemperatureCounter(int id);
//    DeviceTemperatureTransducer getDeviceTemperatureTransducer(int id);
    DeviceInfo getDevice(int id);
    TextWatcher getLastCheckDateListener(EditText date);
    //void setDevice(DeviceInfo newDevice, int id);
}
