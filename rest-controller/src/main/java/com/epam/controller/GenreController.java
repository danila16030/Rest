package com.epam.controller;

import com.epam.assembler.GenreAssembler;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Genre;
import com.epam.model.GenreModel;
import com.epam.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreAssembler genreAssembler;

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "{genreId:[0-9]+}")
    public ResponseEntity<GenreModel> removeGenre(@PathVariable long genreId) {
        genreService.removeGenre(genreId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(headers = {"Accept=application/json"})
    public ResponseEntity<GenreModel> creteNewGenre(@RequestBody @Valid CreateGenreRequestDTO genreDTO) {
        Genre response = genreService.createGenre(genreDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getGenreId()).build().toUri();
        return ResponseEntity.created(location).body(genreAssembler.toModel(response));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<GenreModel> updateGenre(@RequestBody @Valid UpdateGenreRequestDTO genreDTO) {
        Genre response = genreService.updateGenre(genreDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getGenreId()).build().toUri();
        return ResponseEntity.ok().location(location).body(genreAssembler.toModel(response));
    }

    @GetMapping(value = "{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<GenreModel>> getAllGenres(@PathVariable int limit, @PathVariable int offset) {
        return ResponseEntity.ok(genreAssembler.toCollectionModel(genreService.getAllGenres(limit, offset)));
    }

    @GetMapping(value = "{genreId:[0-9]+}")
    public ResponseEntity<GenreModel> getGenre(@PathVariable long genreId) {
        return ResponseEntity.ok(genreAssembler.toModel(genreService.getGenre(genreId)));
    }
}
