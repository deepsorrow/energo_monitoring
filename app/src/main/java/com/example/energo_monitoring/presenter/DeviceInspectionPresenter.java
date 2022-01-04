package com.example.energo_monitoring.presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.model.DeviceCounter;
import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.model.DevicePressureTransducer;
import com.example.energo_monitoring.model.DeviceTemperatureCounter;
import com.example.energo_monitoring.model.DeviceTemperatureTransducer;
import com.example.energo_monitoring.model.api.DeviceInfo;
import com.example.energo_monitoring.model.db.ResultData;
import com.example.energo_monitoring.model.db.ResultDataDatabase;
import com.example.energo_monitoring.view.activity.DeviceInspectionActivity;
import com.example.energo_monitoring.view.DeviceInspectionFragment;
import com.example.energo_monitoring.view.viewmodel.InspectionDeviceViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DeviceInspectionPresenter {
    public DeviceInspectionActivity activity;
    public ArrayList<DeviceInfo> devices;
    public InspectionDeviceViewModel model;
    public ArrayList<DeviceInspectionFragment> fragments;

    public DeviceInspectionPresenter(DeviceInspectionActivity activity, ArrayList<DeviceInfo> devices) {
        this.activity = activity;
        this.devices = devices;

        fragments = new ArrayList<>();
        for(int i=0; i<devices.size(); ++i){
            fragments.add(DeviceInspectionFragment.newInstance(i));
        }
    }

    public Context getContext() {
        return activity.getApplicationContext();
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

    public void replaceCurrentFragment() {
        int currentId = model.getCurrentDeviceId().getValue();

        activity.getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentDevices, fragments.get(currentId))
                .commit();
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

                    //((DeviceInspectionFragment) fragments.get(i).getActivity())

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

                    db.resultDataDAO().insertDeviceTemperatureCounter(deviceTemperatureCounters);
                    db.resultDataDAO().insertDeviceFlowTransducers(deviceFlowTransducers);
                    db.resultDataDAO().insertDeviceTemperatureTransducers(deviceTemperatureTransducers);
                    db.resultDataDAO().insertDevicePressureTransducers(devicePressureTransducers);


                });
    }
}
