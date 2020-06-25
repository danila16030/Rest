package com.epam.services.validator;

import com.epam.daos.dao.impl.fields.BookFields;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParametersValidator {
    public boolean isValid(Map<String, String> map) {
        final boolean[] result = {false};
        map.forEach((k, v) -> {
            if (v.isEmpty() | v.isBlank()) {
                result[0] = false;
            }
            if (!k.equals(BookFields.AUTHOR) && !k.equals(BookFields.PRICE) && !k.equals(BookFields.ID) &&
                    !k.equals(BookFields.DESCRIPTION) && !k.equals(BookFields.NUMBEROFPAGES) &&
                    !k.equals(BookFields.TITLE) && !k.equals(BookFields.WRITINGDATE)) {
                result[0] = false;
            }
        });
        return result[0];
    }
}
