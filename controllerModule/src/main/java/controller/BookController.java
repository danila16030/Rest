package com.epam.controller;

import com.epam.converter.JsonConverter;
import dto.BookDTO;
import dto.ParametersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.impl.BookGenreServiceImpl;
import service.impl.BookServiceImpl;
import service.impl.GenreServiceImpl;


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

    @GetMapping(value = "/getBooksSortedByName")
    public String getBooksSortedByName(Model model) {
        model.addAttribute("books", bookService.getBooksSortedByName());
        return "jsonTemplate";
    }

    @GetMapping(value = "/getBooksSortedByDate")
    public String getBooksSortedByDate(Model model) {
        model.addAttribute("books", bookService.getBooksSortedByDate());
        return "jsonTemplate";
    }

    @GetMapping(value = "/getBook/searchByPartialCoincidence", headers = {"Accept=application/json"})
    public String searchByPartialCoincidence(Model model, @RequestBody String json) {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute("book", bookService.getBookByPartialCoincidence(parametersDTO));
        return "jsonTemplate";
    }

    @GetMapping(value = "/getBook/searchByFullCoincidence", headers = {"Accept=application/json"})
    public String searchByFullCoincidence(Model model, @RequestBody String json) {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute("book", bookService.getBookByFullCoincidence(parametersDTO));
        return "jsonTemplate";
    }

    @GetMapping(value = "/getBook/filter", headers = {"Accept=application/json"})
    public String filterByDate(Model model, @RequestBody String json) {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute("book", bookService.filter(parametersDTO));
        return "jsonTemplate";
    }
}
