package com.epam.controll.controller;

import com.epam.daos.dao.impl.fields.GenreFields;
import com.epam.entytys.dto.ParametersDTO;
import com.epam.entytys.entyty.Book;
import com.epam.entytys.entyty.Genre;
import com.epam.services.service.BookService;
import com.epam.services.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.epam.services.service.impl.BookGenreServiceImpl;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/book_genre")
public class BookGenreController {
    @Autowired
    private BookGenreServiceImpl bookGenreService;

    @Autowired
    private GenreServiceImpl genreService;

    @Autowired
    private BookService bookService;

    private final static String jsonTemplate = "jsonTemplate";

    @GetMapping(value = "/getBookByGenre/{genreName}")
    public String getBookByGenre(Model model, @PathVariable String genreName) {
        int genreId = genreService.getGenre(genreName).getGenreId();
        model.addAttribute("books", bookGenreService.getBooksByGenre(genreId));
        return jsonTemplate;
    }

    @GetMapping(value = "/getGenreByBook/{bookName}")
    public String getGenreByBook(Model model, @PathVariable String bookName) {
        int bookId = bookService.getBookByName(bookName).getBookId();
        model.addAttribute("genres", bookGenreService.getGenresByBook(bookId));
        return jsonTemplate;
    }
}
