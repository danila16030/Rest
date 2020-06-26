package com.epam.controller;

import com.epam.attributes.ModelAttributes;
import com.epam.service.impl.BookGenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/book_genre")
public class BookGenreController {

    private final static String jsonTemplate = "jsonTemplate";

    @Autowired
    private BookGenreServiceImpl bookGenreService;


    @GetMapping(value = "/get/book-by-genre/{genreId}")
    public String getBookByGenre(Model model, @PathVariable long genreId) {
        model.addAttribute(ModelAttributes.BOOKS, bookGenreService.getBooksByGenre(genreId));
        return jsonTemplate;
    }

    @GetMapping(value = "/get/genre-by-book/{bookId}")
    public String getGenreByBook(Model model, @PathVariable long bookId) {
        model.addAttribute(ModelAttributes.GENRES, bookGenreService.getGenresByBook(bookId));
        return jsonTemplate;
    }
}
