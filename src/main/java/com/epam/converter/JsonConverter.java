package com.epam.converter;

import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.dto.ParametersDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Component
public class JsonConverter {
    private ObjectMapper objectMapper = new ObjectMapper();

    public BookDTO convertToBookDTO(String json) {
        try {
            String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
            return objectMapper.readValue(decodedJson, new TypeReference<BookDTO>() {
            });
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new BookDTO();
        }
    }

    public GenreDTO convertToGenreDTO(String json) {
        try {
            String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
            return objectMapper.readValue(decodedJson, new TypeReference<GenreDTO>() {
            });
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new GenreDTO();
        }
    }

    public ParametersDTO convertToParameters(String json) {
        try {
            ParametersDTO parametersDTO = new ParametersDTO();
            String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
            parametersDTO.setParameters(objectMapper.readValue(decodedJson, new TypeReference<HashMap<String, String>>() {
            }));
            return parametersDTO;
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ParametersDTO();
        }
    }

}
