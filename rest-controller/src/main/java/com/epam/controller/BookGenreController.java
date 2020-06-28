package com.epam.controller;

import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.service.impl.BookGenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/book_genre")
public class BookGenreController {

    @Autowired
    private BookGenreServiceImpl bookGenreService;


    @GetMapping(value = "/get/book-by-genre/{genreId}")
    public List<BookDTO> getBookByGenre(Model model, @PathVariable long genreId) {
        return  bookGenreService.getBooksByGenre(genreId);
    }

    @GetMapping(value = "/get/genre-by-book/{bookId}")
    public List<GenreDTO> getGenreByBook(Model model, @PathVariable long bookId) {
        return bookGenreService.getGenresByBook(bookId);
    }
}
