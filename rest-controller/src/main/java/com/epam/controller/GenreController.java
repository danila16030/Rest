package com.epam.controller;

import com.epam.dto.request.CreateGenreRequestDTO;
import com.epam.dto.request.UpdateGenreRequestDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;


    @PostMapping(value = "/remove/{genreId}")
    public ResponseEntity<GenreResponseDTO> removeGenre(@PathVariable long genreId) {
        genreService.removeGenre(genreId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public ResponseEntity<GenreResponseDTO> creteNewGenre(@RequestBody @Valid CreateGenreRequestDTO genreDTO) {
        GenreResponseDTO response = genreService.createGenre(genreDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getGenreId()).build().toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping(value = "/update", headers = {"Accept=application/json"})
    public ResponseEntity<GenreResponseDTO> updateGenre(@RequestBody @Valid UpdateGenreRequestDTO genreDTO) {
        GenreResponseDTO response = genreService.updateGenre(genreDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getGenreId()).build().toUri();
        return ResponseEntity.ok().location(location).body(response);
    }

    @GetMapping(value = "/get/all")
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping(value = "/get/by-name/{genreName}")
    public ResponseEntity<GenreResponseDTO> getGenreByName(@PathVariable String genreName) {
        return ResponseEntity.ok(genreService.getGenre(genreName));
    }
}
