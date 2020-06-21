package com.epam.controll.controller;

import com.epam.controll.attributes.ModelAttributes;
import com.epam.services.service.BookService;
import com.epam.services.service.impl.BookGenreServiceImpl;
import com.epam.services.service.impl.GenreServiceImpl;
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

    @Autowired
    private GenreServiceImpl genreService;

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/getBookByGenre/{genreName}")
    public String getBookByGenre(Model model, @PathVariable String genreName) {
        long genreId = genreService.getGenre(genreName).getGenreId();
        model.addAttribute(ModelAttributes.BOOKS, bookGenreService.getBooksByGenre(genreId));
        return jsonTemplate;
    }

    @GetMapping(value = "/getGenreByBook/{bookName}")
    public String getGenreByBook(Model model, @PathVariable String bookName) {
        long bookId = bookService.getBookByName(bookName).getBookId();
        model.addAttribute(ModelAttributes.GENRES, bookGenreService.getGenresByBook(bookId));
        return jsonTemplate;
    }
}
