package com.epam.controller;

import com.epam.assembler.BookAssembler;
import com.epam.assembler.GenreAssembler;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.model.BookModel;
import com.epam.model.GenreModel;
import com.epam.principal.UserPrincipal;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookAssembler bookAssembler;

    @Autowired
    private GenreAssembler genreAssembler;

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "{bookId:[0-9]+}")
    public ResponseEntity<BookModel> removeBook(@PathVariable long bookId) {
        bookService.removeBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "{bookId:[0-9]+}")
    public ResponseEntity<BookModel> getBook(@PathVariable long bookId,
                                             @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return ResponseEntity.ok(bookAssembler.toBookModel(bookService.getBook(bookId), userPrincipal));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<BookModel> updateBook(@RequestBody @Valid UpdateBookRequestDTO bookDTO,
                                                @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        Book response = bookService.updateBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.ok().location(location).body(bookAssembler.toBookModel(response, userPrincipal));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(headers = {"Accept=application/json"})
    public ResponseEntity<BookModel> creteNewBook(@RequestBody @Valid CreateBookRequestDTO bookDTO,
                                                  @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        Book response = bookService.createBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getBookId()).build().toUri();
        return ResponseEntity.created(location).body(bookAssembler.toBookModel(response, userPrincipal));
    }

    @GetMapping(value = "{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> getAllBooks(@PathVariable int limit, @PathVariable int offset,
                                                                  @AuthenticationPrincipal final
                                                                  UserPrincipal userPrincipal) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getAllBooks(limit, offset),
                userPrincipal));
    }

    @GetMapping(value = "{fistId:[0-9]+},{secondId:[0-9]+},{thirdId:[0-9]+}")
    public ResponseEntity<GenreModel> getTopGenre(@PathVariable long fistId, @PathVariable long secondId,
                                                  @PathVariable long thirdId) {
        Genre genre = bookService.geTheMostCommonGenre(fistId, secondId, thirdId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + genre.getGenreId()).build().toUri();
        return ResponseEntity.created(location).body(genreAssembler.toModel(genre));
    }

    @GetMapping(value = "/by-name/{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> getBooksSortedByName(@PathVariable int limit,
                                                                           @PathVariable int offset,
                                                                           @AuthenticationPrincipal final
                                                                           UserPrincipal userPrincipal) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getBooksSortedByName(limit, offset),
                userPrincipal));
    }

    @GetMapping(value = "/by-partial-coincidence/{title},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> searchByPartialCoincidence(@PathVariable String title,
                                                                                 @PathVariable int limit,
                                                                                 @PathVariable int offset,
                                                                                 @AuthenticationPrincipal final
                                                                                 UserPrincipal userPrincipal) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getBookByPartialCoincidence(title,
                limit, offset), userPrincipal));
    }

    @GetMapping(value = "/by-full-coincidence/{title},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> searchByFullCoincidence(@PathVariable String title,
                                                                              @PathVariable int offset,
                                                                              @PathVariable int limit,
                                                                              @AuthenticationPrincipal final
                                                                              UserPrincipal userPrincipal) {
        return ResponseEntity.ok(bookAssembler.toCollectionModel(bookService.getBookByFullCoincidence(title,
                limit, offset), userPrincipal));
    }
}
