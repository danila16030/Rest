package com.epam.controll.controller;

import com.epam.controll.attributes.ModelAttributes;
import com.epam.controll.converter.JsonConverter;
import com.epam.models.dto.BookDTO;
import com.epam.models.dto.ParametersDTO;
import com.epam.services.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/book")
public class BookController {
    private final static String jsonTemplate = "jsonTemplate";

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private JsonConverter jsonConverter;


    @PostMapping(value = "/remove", headers = {"Accept=application/json"})
    public String removeBook(Model model, @RequestBody String json) {
        model.addAttribute(ModelAttributes.RESULT, bookService.removeBook(jsonConverter.convertToBookDTO(json)));
        return jsonTemplate;
    }

    @PostMapping(value = "/update", headers = {"Accept=application/json"})
    public String updateBook(Model model, @RequestBody String json) {
        model.addAttribute(ModelAttributes.RESULT, bookService.updateBook(jsonConverter.convertToBookDTO(json)));
        return jsonTemplate;
    }

    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public String creteNewBook(Model model, @RequestBody String json) {
        BookDTO bookDTO = jsonConverter.convertToBookDTO(json);
        model.addAttribute(ModelAttributes.RESULT, bookService.createBook(bookDTO));
        return jsonTemplate;
    }

    @GetMapping(value = "/get/all")
    public String getAllBooks(Model model) {
        model.addAttribute(ModelAttributes.BOOKS, bookService.getAllBooks());
        return jsonTemplate;
    }

    @GetMapping(value = "/get/sortedByName")
    public String getBooksSortedByName(Model model) {
        model.addAttribute(ModelAttributes.BOOKS, bookService.getBooksSortedByName());
        return jsonTemplate;
    }

    @GetMapping(value = "/get/sortedByDate")
    public String getBooksSortedByDate(Model model) {
        model.addAttribute(ModelAttributes.BOOKS, bookService.getBooksSortedByDate());
        return jsonTemplate;
    }

    @GetMapping(value = "/search/byPartialCoincidence", headers = {"Accept=application/json"})
    public String searchByPartialCoincidence(Model model, @RequestBody String json) {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute(ModelAttributes.BOOK, bookService.getBookByPartialCoincidence(parametersDTO));
        return jsonTemplate;
    }

    @GetMapping(value = "/search/byFullCoincidence", headers = {"Accept=application/json"})
    public String searchByFullCoincidence(Model model, @RequestBody String json) {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute(ModelAttributes.BOOK, bookService.getBookByFullCoincidence(parametersDTO));
        return jsonTemplate;
    }

    @GetMapping(value = "/filter", headers = {"Accept=application/json"})
    public String filterByDate(Model model, @RequestBody String json) {
        ParametersDTO parametersDTO = jsonConverter.convertToParameters(json);
        model.addAttribute(ModelAttributes.BOOK, bookService.filter(parametersDTO));
        return jsonTemplate;
    }
}
