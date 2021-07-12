package com.example.energo_monitoring.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.model.DeviceCounter;
import com.example.energo_monitoring.model.TemperatureCounterCharacteristicsParameter;
import com.example.energo_monitoring.view.adapters.TemperatureCounterValuesMainListAdapter;
import com.example.energo_monitoring.view.adapters.TemperatureCounterValuesTabsAdapter;
import com.example.energo_monitoring.view.viewmodel.TemperatureCounterViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TemperatureCounterCharacteristicsPresenter {

    Context context;
    private int currentDevice;
    private List<DeviceCounter> devices;
    private ArrayList<ArrayList<TemperatureCounterCharacteristicsParameter>> parameters;
    TemperatureCounterValuesTabsAdapter adapterTabs;
    TemperatureCounterValuesMainListAdapter adapterParameters;
    TemperatureCounterViewModel model;

    public TemperatureCounterCharacteristicsPresenter(Context context,
                                                      TemperatureCounterViewModel model) {
        this.context = context;
        this.model   = model;

        SharedPreferences mPrefs = context.getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("currentClientDataBundle", "");
        ClientDataBundle clientDataBundle = gson.fromJson(json, ClientDataBundle.class);

        devices = clientDataBundle.getDeviceCounters();

        parameters = new ArrayList<>();
        for(DeviceCounter deviceCounter : devices) {
            ArrayList<TemperatureCounterCharacteristicsParameter> newParameters = new ArrayList<>();
            newParameters.add(new TemperatureCounterCharacteristicsParameter("Qo Гкал", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("Q гвс", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("G1 т/час", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("G2 т/час", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("G3 т/час", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("T1 C", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("T2 C", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("P1", ""));
            newParameters.add(new TemperatureCounterCharacteristicsParameter("P2", ""));
            parameters.add(newParameters);
        }
    }

    public List<DeviceCounter> getDevices() {
        return devices;
    }

    public ArrayList<TemperatureCounterCharacteristicsParameter> getCurrentParameters() {
        return parameters.get(currentDevice);
    }

    public void setAdapters(TemperatureCounterValuesTabsAdapter adapterTabs,
                            TemperatureCounterValuesMainListAdapter adapterParameters) {
        this.adapterTabs = adapterTabs;
        this.adapterParameters = adapterParameters;
    }

    public TemperatureCounterValuesTabsAdapter getAdapterTabs() {
        return adapterTabs;
    }

    public TemperatureCounterValuesMainListAdapter getAdapterParameters() {
        return adapterParameters;
    }

    public int getCurrentDevice() {
        return currentDevice;
    }

    public void setCurrentDevice(int currentDevice) {
        this.currentDevice = currentDevice;
    }

    public TemperatureCounterViewModel getModel(){
        return model;
    }
}
