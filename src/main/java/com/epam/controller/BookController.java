package com.epam.controller;

import com.epam.dto.BookDTO;
import com.epam.service.impl.BookGenreServiceImpl;
import com.epam.service.impl.BookServiceImpl;
import com.epam.service.impl.GenreServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private BookGenreServiceImpl bookGenreService;
    @Autowired
    private GenreServiceImpl genreService;

    private BookDTO getBook(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String decodedJson = java.net.URLDecoder.decode(json, "UTF-8");
            return objectMapper.readValue(decodedJson, new TypeReference<BookDTO>() {
            });
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new BookDTO();
        }
    }

    @PostMapping(value = "/removeBook", headers = {"Accept=application/json"})
    public String removeBook(Model model, @RequestBody String json) {
        model.addAttribute("result", bookService.removeBook(getBook(json).getTitle()));
        return "jsonTemplate";
    }

    @PostMapping(value = "/updateBook", headers = {"Accept=application/json"})
    public String updateBook(Model model, @RequestBody String json) {
        model.addAttribute("result", bookService.updateBook(getBook(json)));
        return "jsonTemplate";
    }

    @PostMapping(value = "/createNewBook", headers = {"Accept=application/json"})
    public String creteNewGenre(Model model, @RequestBody String json) {
        BookDTO bookDTO = getBook(json);
        int bookId = bookService.createBook(bookDTO);
        if (bookId == 0) {
            model.addAttribute("result", true);
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

    @GetMapping(value = "/getBook/{bookName}")
    public String getBookByName(Model model, @PathVariable String bookName) {
        model.addAttribute("book", bookService.getBook(bookName));
        return "jsonTemplate";
    }
}
