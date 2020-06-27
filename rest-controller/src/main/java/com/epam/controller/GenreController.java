package com.epam.controller;

import com.epam.attributes.ModelAttributes;
import com.epam.dto.GenreDTO;
import com.epam.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/genre")
public class GenreController {
    private final static String jsonTemplate = "jsonTemplate";

    @Autowired
    private GenreServiceImpl genreService;


    @PostMapping(value = "/remove", headers = {"Accept=application/json"})
    public String removeGenre(Model model, @RequestBody GenreDTO genreDTO) {
        model.addAttribute(ModelAttributes.RESULT, genreService.removeGenre(genreDTO));
        return jsonTemplate;
    }

    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public String creteNewGenre(Model model, @RequestBody GenreDTO genreDTO) {
        model.addAttribute(ModelAttributes.RESULT, genreService.createGenre(genreDTO));
        return jsonTemplate;
    }

    @GetMapping(value = "/get/all")
    public String getAllGenres(Model model) {
        model.addAttribute(ModelAttributes.GENRES, genreService.getAllGenres());
        return jsonTemplate;
    }

    @GetMapping(value = "/get/by-name/{genreName}")
    public String getBookByName(Model model, @PathVariable String genreName) {
        model.addAttribute(ModelAttributes.GENRE, genreService.getGenre(genreName));
        return jsonTemplate;
    }
}
