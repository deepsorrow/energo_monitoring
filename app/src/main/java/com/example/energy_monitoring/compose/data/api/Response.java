
package com.example.energy_monitoring.compose.data.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

    private Integer numFound;
    private Integer start;
    private List<Doc> docs = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getNumFound() {
        return numFound;
    }

    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
