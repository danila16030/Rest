package com.epam.controller;

import com.epam.attributes.ModelAttributes;
import com.epam.converter.JsonConverter;
import com.epam.dto.BookDTO;
import com.epam.dto.ParametersDTO;
import com.epam.exception.InvalidDataException;
import com.epam.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/book")
public class BookController {
    private final static String jsonTemplate = "jsonTemplate";

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private JsonConverter jsonConverter;

    @PostMapping(value = "/remove", headers = {"Accept=application/json"})
    public String removeBook(Model model, @RequestBody String json) throws InvalidDataException {
        model.addAttribute(ModelAttributes.RESULT, bookService.removeBook(jsonConverter.convertToBookDTO(json)));
        return jsonTemplate;
    }

    @PostMapping(value = "/update", headers = {"Accept=application/json"})
    public String updateBook(Model model, @RequestBody String json) throws InvalidDataException {
        model.addAttribute(ModelAttributes.RESULT, bookService.updateBook(jsonConverter.convertToBookDTO(json)));
        return jsonTemplate;
    }

    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public String creteNewBook(Model model, @RequestBody String json) throws InvalidDataException {
        BookDTO bookDTO = jsonConverter.convertToBookDTO(json);
        model.addAttribute(ModelAttributes.RESULT, bookService.createBook(bookDTO));
        return jsonTemplate;
    }

    @GetMapping(value = "/get/all")
    public String getAllBooks(Model model) {
        model.addAttribute(ModelAttributes.BOOKS, bookService.getAllBooks());
        return jsonTemplate;
    }

    @GetMapping(value = "/get/sorted/by-name")
    public String getBooksSortedByName(Model model) {
        model.addAttribute(ModelAttributes.BOOKS, bookService.getBooksSortedByName());
        return jsonTemplate;
    }

    @GetMapping(value = "/get/sorted/by-date")
    public String getBooksSortedByDate(Model model) {
        model.addAttribute(ModelAttributes.BOOKS, bookService.getBooksSortedByDate());
        return jsonTemplate;
    }

    @GetMapping(value = "/search/by-partial-coincidence", headers = {"Accept=application/json"})
    public String searchByPartialCoincidence(Model model, @RequestBody String json) throws InvalidDataException {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute(ModelAttributes.BOOK, bookService.getBookByPartialCoincidence(parametersDTO));
        return jsonTemplate;
    }

    @GetMapping(value = "/search/by-full-coincidence", headers = {"Accept=application/json"})
    public String searchByFullCoincidence(Model model, @RequestBody String json) throws InvalidDataException {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute(ModelAttributes.BOOK, bookService.getBookByFullCoincidence(parametersDTO));
        return jsonTemplate;
    }

    @GetMapping(value = "/filter", headers = {"Accept=application/json"})
    public String filterByDate(Model model, @RequestBody String json) throws InvalidDataException {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute(ModelAttributes.BOOK, bookService.filter(parametersDTO));
        return jsonTemplate;
    }
}
