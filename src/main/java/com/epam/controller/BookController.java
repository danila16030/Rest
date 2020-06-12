package com.epam.controller;

import com.epam.converter.JsonConverter;
import com.epam.dto.BookDTO;
import com.epam.service.impl.BookGenreServiceImpl;
import com.epam.service.impl.BookServiceImpl;
import com.epam.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private BookGenreServiceImpl bookGenreService;
    @Autowired
    private GenreServiceImpl genreService;
    @Autowired
    private JsonConverter jsonConverter = new JsonConverter();


    @PostMapping(value = "/removeBook/{bookName}")
    public String removeBook(Model model, @PathVariable String bookName) {
        model.addAttribute("result", bookService.removeBook(bookName));
        return "jsonTemplate";
    }

    @PostMapping(value = "/updateBook", headers = {"Accept=application/json"})
    public String updateBook(Model model, @RequestBody String json) {
        model.addAttribute("result", bookService.updateBook(jsonConverter.convertToBookDTO(json)));
        return "jsonTemplate";
    }

    @PostMapping(value = "/createNewBook", headers = {"Accept=application/json"})
    public String creteNewGenre(Model model, @RequestBody String json) {
        BookDTO bookDTO = jsonConverter.convertToBookDTO(json);
        int bookId = bookService.createBook(bookDTO);
        if (bookId == 0) {
            model.addAttribute("result", false);
            return "jsonTemplate";
        }
        String genreName = bookDTO.getGenre().getGenreName();
        int genreId = genreService.getGenreId(genreName);
        bookGenreService.createConnection(bookId, genreId);
        model.addAttribute("result", true);
        return "jsonTemplate";
    }

    @GetMapping(value = "/getAllBooks")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "jsonTemplate";
    }

    @GetMapping(value = "/getBook/PartialName/{name}", headers = {"Accept=application/json"})
    public String getBookByPartialName(Model model, @PathVariable String name) {
        model.addAttribute("book", bookService.getBookByPartialCoincidence(name));
        return "jsonTemplate";
    }

    @GetMapping(value = "/getBook/FullName/{name}", headers = {"Accept=application/json"})
    public String getBookByFullName(Model model, @PathVariable String name) {
        model.addAttribute("book", bookService.getBookByFullCoincidence(name));
        return "jsonTemplate";
    }
}
