package com.example.energo_monitoring.presenter;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.model.api.DeviceInfo;
import com.example.energo_monitoring.view.activity.DeviceInspectionActivity;
import com.example.energo_monitoring.view.adapters.DeviceInspectionFragment;
import com.example.energo_monitoring.view.viewmodel.InspectionDeviceViewModel;

import java.util.ArrayList;
import java.util.Calendar;

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

    public TextWatcher getLastCheckDateTextWatcher(EditText date){
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
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
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
}
