package com.epam.controller;

import com.epam.assembler.GenreAssembler;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Genre;
import com.epam.model.GenreModel;
import com.epam.principal.UserPrincipal;
import com.epam.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<GenreModel> creteNewGenre(@RequestBody @Valid CreateGenreRequestDTO genreDTO,
                                                    @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        Genre response = genreService.createGenre(genreDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getGenreId()).build().toUri();
        return ResponseEntity.created(location).body(genreAssembler.toGenreModel(response, userPrincipal));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<GenreModel> updateGenre(@RequestBody @Valid UpdateGenreRequestDTO genreDTO,
                                                  @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        Genre response = genreService.updateGenre(genreDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getGenreId()).build().toUri();
        return ResponseEntity.ok().location(location).body(genreAssembler.toGenreModel(response, userPrincipal));
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<GenreModel>> getAllGenres(@RequestParam(defaultValue = "10") int limit,
                                                                    @RequestParam(defaultValue = "0") int offset,
                                                                    @AuthenticationPrincipal final
                                                                    UserPrincipal userPrincipal) {
        return ResponseEntity.ok(genreAssembler.toCollectionModel(genreService.getAllGenres(limit, offset), userPrincipal));
    }

    @GetMapping(value = "{genreId:[0-9]+}")
    public ResponseEntity<GenreModel> getGenre(@PathVariable long genreId,
                                               @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return ResponseEntity.ok(genreAssembler.toGenreModel(genreService.getGenre(genreId), userPrincipal));
    }

    @GetMapping(value = "{genreName}")
    public ResponseEntity<GenreModel> getGenreByName(@PathVariable String genreName,
                                                     @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return ResponseEntity.ok().headers(responseHeaders).body(genreAssembler.toGenreModel(genreService.getGenre(genreName), userPrincipal));
    }
}
