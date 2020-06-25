package com.epam.controll.converter;

import com.epam.controll.exception.ArgumentsNotValidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.epam.models.dto.BookDTO;
import com.epam.models.dto.GenreDTO;
import com.epam.models.dto.ParametersDTO;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
            return parametersDTO;
        } catch (IOException e) {
            throw new ArgumentsNotValidException();
        }
    }

}
