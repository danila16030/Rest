package com.epam.controller;

import com.epam.dto.responce.BookResponseDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


    @GetMapping(value = "/get/book-by-genre/{genreId}")
    public ResponseEntity<List<BookResponseDTO>> getBookByGenre(@PathVariable long genreId) {
        return ResponseEntity.ok(bookGenreService.getBooksByGenre(genreId));
    }

    @GetMapping(value = "/get/genre-by-book/{bookId}")
    public ResponseEntity<List<GenreResponseDTO>> getGenreByBook(@PathVariable long bookId) {
        return ResponseEntity.ok(bookGenreService.getGenresByBook(bookId));
    }
}
