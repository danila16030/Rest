package com.epam.controller;

import com.epam.assembler.BookAssembler;
import com.epam.assembler.GenreAssembler;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.model.BookModel;
import com.epam.model.GenreModel;
import com.epam.principal.UserPrincipal;
import com.epam.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/book_genre")
public class BookGenreController {

    @Autowired
    private BookGenreService bookGenreService;
    @Autowired
    private GenreAssembler genreAssembler;

    @Autowired
    private BookAssembler bookAssembler;

    @GetMapping(value = "/books-by-genre/{genreId:[0-9]+},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<BookModel>> getBookByGenre(@PathVariable long genreId, @PathVariable int limit,
                                                                     @PathVariable int offset,
                                                                     @AuthenticationPrincipal final
                                                                     UserPrincipal userPrincipal) {
        List<Book> books = bookGenreService.getBooksByGenre(genreId, limit, offset);
        return ResponseEntity.ok(bookAssembler.toCollection(books, userPrincipal));
    }

    @GetMapping(value = "/genres-by-book/{bookId:[0-9]+},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<GenreModel>> getGenreByBook(@PathVariable long bookId, @PathVariable int limit,
                                                                      @PathVariable int offset,
                                                                      @AuthenticationPrincipal final
                                                                      UserPrincipal userPrincipal) {
        List<Genre> genres = bookGenreService.getGenresByBook(bookId, limit, offset);
        return ResponseEntity.ok(genreAssembler.toCollection(genres, userPrincipal));
    }

}
