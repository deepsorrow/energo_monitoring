package com.example.energo_monitoring.model.api;

import com.example.energo_monitoring.model.DeviceCounter;
import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.model.DevicePressureTransducer;
import com.example.energo_monitoring.model.DeviceTemperatureCounter;
import com.example.energo_monitoring.model.DeviceTemperatureTransducer;

import java.io.Serializable;
import java.util.List;

public class ClientDataBundle implements Serializable {
    private ClientInfo clientInfo;
    private ProjectDescription project;
    private List<DeviceTemperatureCounter> deviceTemperatureCounters;
    private List<DeviceFlowTransducer> deviceFlowTransducers;
    private List<DeviceTemperatureTransducer> deviceTemperatureTransducers;
    private List<DevicePressureTransducer> devicePressureTransducers;
    private List<DeviceCounter> deviceCounters;

    public ClientDataBundle() {
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public ProjectDescription getProject() {
        return project;
    }

    public void setProject(ProjectDescription project) {
        this.project = project;
    }

    public List<DeviceTemperatureCounter> getDeviceTemperatureCounters() {
        return deviceTemperatureCounters;
    }

    public void setDeviceTemperatureCounters(List<DeviceTemperatureCounter> deviceTemperatureCounters) {
        this.deviceTemperatureCounters = deviceTemperatureCounters;
    }

    public List<DeviceFlowTransducer> getDeviceFlowTransducers() {
        return deviceFlowTransducers;
    }

    public void setDeviceFlowTransducers(List<DeviceFlowTransducer> deviceFlowTransducers) {
        this.deviceFlowTransducers = deviceFlowTransducers;
    }

    public List<DeviceTemperatureTransducer> getDeviceTemperatureTransducers() {
        return deviceTemperatureTransducers;
    }

    public void setDeviceTemperatureTransducers(List<DeviceTemperatureTransducer> deviceTemperatureTransducers) {
        this.deviceTemperatureTransducers = deviceTemperatureTransducers;
    }

    public List<DevicePressureTransducer> getDevicePressureTransducers() {
        return devicePressureTransducers;
    }

    public void setDevicePressureTransducers(List<DevicePressureTransducer> devicePressureTransducers) {
        this.devicePressureTransducers = devicePressureTransducers;
    }

    public List<DeviceCounter> getDeviceCounters() {
        return deviceCounters;
    }

    public void setDeviceCounters(List<DeviceCounter> deviceCounters) {
        this.deviceCounters = deviceCounters;
    }
}
