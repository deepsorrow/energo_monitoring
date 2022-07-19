
package com.example.energy_monitoring.compose.data.api;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {

    private Integer status;
    private Integer qTime;
    private Params params;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getQTime() {
        return qTime;
    }

    public void setQTime(Integer qTime) {
        this.qTime = qTime;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
