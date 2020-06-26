package com.epam.converter;

import com.epam.exception.ArgumentsNotValidException;
import com.epam.dao.impl.fields.BookFields;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.dto.ParametersDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

@Component
public class JsonConverter {
    private ObjectMapper objectMapper = new ObjectMapper();

    public BookDTO convertToBookDTO(String json) {
        try {
            String decodedJson = URLDecoder.decode(json, "UTF-8");
            return objectMapper.readValue(decodedJson, new TypeReference<BookDTO>() {
            });
        } catch (IOException e) {
            throw new ArgumentsNotValidException();
        }
    }

    public GenreDTO convertToGenreDTO(String json) {
        try {
            String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
            return objectMapper.readValue(decodedJson, new TypeReference<GenreDTO>() {
            });
        } catch (IOException e) {
            throw new ArgumentsNotValidException();
        }
    }

    public ParametersDTO convertToParameters(String json) {
        try {
            ParametersDTO parametersDTO = new ParametersDTO();
            String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
            parametersDTO.setParameters(objectMapper.readValue(decodedJson, new TypeReference<HashMap<String, String>>() {
            }));
            parametersDTO.getParameters().forEach((k, v) -> {
                if (!k.equals(BookFields.AUTHOR) && !k.equals(BookFields.PRICE) && !k.equals(BookFields.ID) &&
                        !k.equals(BookFields.DESCRIPTION) && !k.equals(BookFields.NUMBEROFPAGES) &&
                        !k.equals(BookFields.TITLE) && !k.equals(BookFields.WRITINGDATE)) {
                    throw new ArgumentsNotValidException();
                }
            });
            return parametersDTO;
        } catch (IOException e) {
            throw new ArgumentsNotValidException();
        }
    }

}
