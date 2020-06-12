package com.epam.controller;

import com.epam.dto.GenreDTO;
import com.epam.entyty.Genre;
import com.epam.service.impl.GenreServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping(value = "/genre")
public class GenreController {
    @Autowired
    private GenreServiceImpl genreService;

    private GenreDTO getGenre(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
            return objectMapper.readValue(decodedJson, new TypeReference<GenreDTO>() {
            });
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new GenreDTO();
        }
    }

    @PostMapping(value = "/removeGenre", headers = {"Accept=application/json"})
    public String removeGenre(Model model, @RequestBody String json) {
        model.addAttribute("result", genreService.removeGenre(getGenre(json).getGenreName()));
        return "jsonTemplate";
    }

    @PostMapping(value = "/createNewGenre", headers = {"Accept=application/json"})
    public String creteNewGenre(Model model,@RequestBody String json) {
        model.addAttribute("result", genreService.createGenre(getGenre(json)));
        return "jsonTemplate";
    }

    @GetMapping(value = "/getAllGenres")
    public String getAllGenres(Model model) {
        model.addAttribute("genres", genreService.getAllGenres());
        return "jsonTemplate";
    }

    @GetMapping(value = "/getGenre/{genreName}")
    public String getBookByName(Model model, @PathVariable String genreName) {
        model.addAttribute("genre", genreService.getGenre(genreName));
        return "jsonTemplate";
    }
}
