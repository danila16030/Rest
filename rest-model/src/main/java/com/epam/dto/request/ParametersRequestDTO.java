package com.epam.dto.request;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;


public class ParametersRequestDTO {
    @NotEmpty(message = "Incorrect parameters(Should contain some information)")
    HashMap<String, String> parameters;

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }
}
