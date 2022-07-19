
package com.example.energy_monitoring.compose.data.api;

import java.util.HashMap;
import java.util.Map;

public class ModelDetailed {

    private ResponseHeader responseHeader;
    private Response response;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
