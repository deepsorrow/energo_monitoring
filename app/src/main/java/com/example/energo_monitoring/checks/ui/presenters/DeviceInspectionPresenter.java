package com.example.energo_monitoring.checks.ui.presenters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.energo_monitoring.checks.data.devices.DeviceFlowTransducer;
import com.example.energo_monitoring.checks.data.devices.DevicePressureTransducer;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureCounter;
import com.example.energo_monitoring.checks.data.devices.DeviceTemperatureTransducer;
import com.example.energo_monitoring.checks.data.api.DeviceInfo;
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase;
import com.example.energo_monitoring.checks.ui.fragments.screens.Step4_DeviceInspectionFragment;
import com.example.energo_monitoring.checks.ui.viewmodel.InspectionDeviceViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DeviceInspectionPresenter {
    public Step4_DeviceInspectionFragment fragment;
    public List<DeviceInfo> devices;
    public InspectionDeviceViewModel model;

    public DeviceInspectionPresenter(Step4_DeviceInspectionFragment fragment, List<DeviceInfo> devices) {
        this.fragment = fragment;
        this.devices = devices;
    }

    public Context getContext() {
        return fragment.requireContext();
    }

    public TextWatcher getLastCheckDateTextWatcher(EditText date, DeviceInfo device){
        return new TextWatcher() {
            String current = "";
            String ddmmyyyy = "DDMMYYYY";
            Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29.02.2012
                        //would be automatically corrected to 28.02.2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s.%s.%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                    device.setLastCheckDate(current);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
    }

//    public void replaceCurrentFragment() {
//        int currentId = model.getCurrentDeviceId().getValue();
//
//        int actionId = 0;
//        switch (devices.get(currentId).getTypeId()){
//            case 1: actionId = R.id.flowTransducer;
//            case 2: actionId = R.id.deviceTemperature;
//        }
//        Navigation
//                .findNavController(fragment.getView())
//                .navigate(actionId);
//    }

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
