package com.epam.controll.controller;

import com.epam.services.service.impl.GenreServiceImpl;
import com.epam.controll.converter.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/genre")
public class GenreController {
    @Autowired
    private GenreServiceImpl genreService;
    @Autowired
    private JsonConverter jsonConverter = new JsonConverter();


    @PostMapping(value = "/removeGenre", headers = {"Accept=application/json"})
    public String removeGenre(Model model, @RequestBody String json) {
        model.addAttribute("result", genreService.removeGenre(jsonConverter.convertToGenreDTO(json).getGenreName()));
        return "jsonTemplate";
    }

    @PostMapping(value = "/createNewGenre", headers = {"Accept=application/json"})
    public String creteNewGenre(Model model, @RequestBody String json) {
        model.addAttribute("result", genreService.createGenre(jsonConverter.convertToGenreDTO(json)));
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
