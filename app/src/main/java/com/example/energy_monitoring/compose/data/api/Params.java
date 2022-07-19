
package com.example.energy_monitoring.compose.data.api;

import java.util.HashMap;
import java.util.Map;

public class Params {

    private String q;
    private String fl;
    private String fq;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFq() {
        return fq;
    }

    public void setFq(String fq) {
        this.fq = fq;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
