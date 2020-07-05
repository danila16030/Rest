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


    @GetMapping(value = "book-by-genre/{genreId:[0-9]+},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<List<BookResponseDTO>> getBookByGenre(@PathVariable long genreId, @PathVariable int limit,
                                                                @PathVariable int offset) {
        return ResponseEntity.ok(bookGenreService.getBooksByGenre(genreId,limit,offset));
    }

    @GetMapping(value = "genre-by-book/{bookId:[0-9]+},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<List<GenreResponseDTO>> getGenreByBook(@PathVariable long bookId, @PathVariable int limit,
                                                                 @PathVariable int offset) {
        return ResponseEntity.ok(bookGenreService.getGenresByBook(bookId,limit,offset));
    }
}
