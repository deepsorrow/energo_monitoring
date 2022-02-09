package com.example.energo_monitoring.presentation.presenters;

import android.text.TextWatcher;
import android.widget.EditText;

import com.example.energo_monitoring.data.api.DeviceInfo;

public interface DeviceInspectionInterface {
//    DeviceFlowTransducer getDeviceFlowTransducer(int id);
//    DevicePressureTransducer getDevicePressureTransducer(int id);
//    DeviceTemperatureCounter getDeviceTemperatureCounter(int id);
//    DeviceTemperatureTransducer getDeviceTemperatureTransducer(int id);
    DeviceInfo getDevice(int id);
    TextWatcher getLastCheckDateListener(EditText date, DeviceInfo device);
    //void setDevice(DeviceInfo newDevice, int id);
}
