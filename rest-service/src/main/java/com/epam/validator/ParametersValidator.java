package com.epam.validator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParametersValidator {
    public boolean isValid(Map<String, String> map) {
        final boolean[] result = {true};
        map.forEach((k, v) -> {
            if (v.isEmpty() | v.isBlank()) {
                result[0] = false;
            }

        });
        return result[0];
    }
}
