package com.epam.dto.request;

import java.util.HashMap;
import java.util.Map;


public class ParametersRequestDTO {
    Map<String, String> parameters = new HashMap<String, String>();

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
