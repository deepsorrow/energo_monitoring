package com.example.energy_monitoring.checks.data.api;

import com.example.energy_monitoring.checks.data.devices.DeviceCounter;
import com.example.energy_monitoring.checks.data.devices.DeviceFlowTransducer;
import com.example.energy_monitoring.checks.data.devices.DevicePressureTransducer;
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureCounter;
import com.example.energy_monitoring.checks.data.devices.DeviceTemperatureTransducer;

import java.io.Serializable;
import java.util.List;

public class ClientDataBundle implements Serializable {
    public int id;
    public int organizationId;
    public int clientId;
    public int version;
    private ClientInfo clientInfo;
    public OrganizationInfo organizationInfo;
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
