package com.epam.controller;

import com.epam.dto.GenreDTO;
import com.epam.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/genre")
public class GenreController {

    @Autowired
    private GenreServiceImpl genreService;


    @PostMapping(value = "/remove", headers = {"Accept=application/json"})
    public boolean removeGenre(Model model,@RequestBody GenreDTO genreDTO) {
        return  genreService.removeGenre(genreDTO);
    }

    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public GenreDTO creteNewGenre(Model model, @RequestBody GenreDTO genreDTO) {
        return genreService.createGenre(genreDTO);
    }

    @GetMapping(value = "/get/all")
    public List<GenreDTO> getAllGenres(Model model) {
        return genreService.getAllGenres();
    }

    @GetMapping(value = "/get/by-name/{genreName}")
    public GenreDTO getGenreByName(Model model, @PathVariable String genreName) {
        return genreService.getGenre(genreName);
    }
}
