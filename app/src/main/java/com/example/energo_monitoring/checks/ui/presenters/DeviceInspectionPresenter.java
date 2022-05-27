package com.example.energo_monitoring.checks.ui.presenters;

import android.content.Context;

import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer;
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureCounter;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer;
import com.example.energo_monitoring.checks.data.api.DeviceInfo;
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase;
import com.example.energo_monitoring.checks.ui.fragments.screens.DeviceInspectionFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DeviceInspectionPresenter {
    public DeviceInspectionFragment fragment;
    public List<DeviceInfo> devices;

    public DeviceInspectionPresenter(DeviceInspectionFragment fragment, List<DeviceInfo> devices) {
        this.fragment = fragment;
        this.devices = devices;
    }

    public Context getContext() {
        return fragment.requireContext();
    }

    public void insertDataToDb(int dataId){
        List<DeviceTemperatureCounter> deviceTemperatureCounters = new ArrayList<>();
        List<DeviceFlowTransducer> deviceFlowTransducers = new ArrayList<>();
        List<DeviceTemperatureTransducer> deviceTemperatureTransducers = new ArrayList<>();
        List<DevicePressureTransducer> devicePressureTransducers = new ArrayList<>();
        for(int i = 0; i < devices.size(); ++i) {
            DeviceInfo device = devices.get(i);
            switch(device.getTypeId()){
                case 1:
                    DeviceTemperatureCounter device1 = (DeviceTemperatureCounter) device;
                    device1.dataId = dataId;
                    deviceTemperatureCounters.add(device1);

                    break;
                case 2:
                    DeviceFlowTransducer device2 = (DeviceFlowTransducer) device;
                    device2.dataId = dataId;
                    deviceFlowTransducers.add(device2);
                    break;
                case 3:
                    DeviceTemperatureTransducer device3 = (DeviceTemperatureTransducer) device;
                    device3.dataId = dataId;
                    deviceTemperatureTransducers.add(device3);
                    break;
                case 4:
                    DevicePressureTransducer device4 = (DevicePressureTransducer) device;
                    device4.dataId = dataId;
                    devicePressureTransducers.add(device4);
                    break;
            }
        }

        ResultDataDatabase db = ResultDataDatabase.getDatabase(getContext());
        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe((value) -> {
                    db.resultDataDAO().deleteDeviceTemperatureCounter(dataId);
                    db.resultDataDAO().deleteDeviceFlowTransducers(dataId);
                    db.resultDataDAO().deleteDeviceTemperatureTransducers(dataId);
                    db.resultDataDAO().deleteDevicePressureTransducers(dataId);

                    db.resultDataDAO().insertDeviceTemperatureCounters(deviceTemperatureCounters);
                    db.resultDataDAO().insertDeviceFlowTransducers(deviceFlowTransducers);
                    db.resultDataDAO().insertDeviceTemperatureTransducers(deviceTemperatureTransducers);
                    db.resultDataDAO().insertDevicePressureTransducers(devicePressureTransducers);
                });
    }
}
