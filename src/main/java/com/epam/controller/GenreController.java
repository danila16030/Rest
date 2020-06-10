package com.epam.controller;

import com.epam.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/genre")
public class GenreController {
    @Autowired
    private GenreServiceImpl genreService;

    @RequestMapping(value = "/getAllGenres")
    public String getAllBooksJSON(Model model) {
        model.addAttribute("genres", genreService.getAllGenres());
        return "jsonTemplate";
    }

    @RequestMapping(value = "/getGenre/{genreName}")
    public String getBookByName(Model model, @PathVariable String genreName) {
        model.addAttribute("genre", genreService.getGenre(genreName));
        return "jsonTemplate";
    }
}
