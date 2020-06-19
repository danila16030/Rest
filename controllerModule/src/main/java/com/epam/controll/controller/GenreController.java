package com.epam.controll.controller;

import com.epam.controll.converter.JsonConverter;
import com.epam.entytys.dto.GenreDTO;
import com.epam.services.service.impl.GenreServiceImpl;
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

    private final static String jsonTemplate = "jsonTemplate";

    @PostMapping(value = "/removeGenre", headers = {"Accept=application/json"})
    public String removeGenre(Model model, @RequestBody String json) {
        GenreDTO genreDTO=jsonConverter.convertToGenreDTO(json);
        model.addAttribute("result", genreService.removeGenre(genreDTO.getGenreName()));
        return jsonTemplate;
    }

    @PostMapping(value = "/createNewGenre", headers = {"Accept=application/json"})
    public String creteNewGenre(Model model, @RequestBody String json) {
        GenreDTO genreDTO=jsonConverter.convertToGenreDTO(json);
        model.addAttribute("result",genreService.createGenre(genreDTO.getGenreName()));
        return jsonTemplate;
    }

    @GetMapping(value = "/getAllGenres")
    public String getAllGenres(Model model) {
        model.addAttribute("genres", genreService.getAllGenres());
        return jsonTemplate;
    }

    @GetMapping(value = "/getGenre/{genreName}")
    public String getBookByName(Model model, @PathVariable String genreName) {
        model.addAttribute("genre", genreService.getGenre(genreName));
        return jsonTemplate;
    }
}
