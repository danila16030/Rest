package com.epam.controller;

import com.epam.dto.BookDTO;
import com.epam.dto.ParametersDTO;
import com.epam.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;


    @PostMapping(value = "/remove", headers = {"Accept=application/json"})
    public boolean removeBook(Model model, @RequestBody BookDTO bookDTO) {
        return  bookService.removeBook(bookDTO);
    }

    @PostMapping(value = "/update", headers = {"Accept=application/json"})
    public BookDTO updateBook(Model model, @RequestBody BookDTO bookDTO) {
        return bookService.updateBook(bookDTO);
    }

    @PostMapping(value = "/create", headers = {"Accept=application/json"})
    public BookDTO creteNewBook(Model model, @RequestBody BookDTO bookDTO){
        return bookService.createBook(bookDTO);
    }

    @GetMapping(value = "/get/all")
    public List<BookDTO> getAllBooks(Model model) {
          return bookService.getAllBooks();
    }

    @GetMapping(value = "/get/sorted/by-name")
    public List<BookDTO> getBooksSortedByName(Model model) {
        return  bookService.getBooksSortedByName();
    }

    @GetMapping(value = "/get/sorted/by-date")
    public List<BookDTO> getBooksSortedByDate(Model model) {
        return bookService.getBooksSortedByDate();
    }

    @GetMapping(value = "/search/by-partial-coincidence", headers = {"Accept=application/json"})
    public List<BookDTO> searchByPartialCoincidence(Model model, @RequestBody  ParametersDTO parametersDTO){
        return bookService.getBookByPartialCoincidence(parametersDTO);
    }

    @GetMapping(value = "/search/by-full-coincidence", headers = {"Accept=application/json"})
    public List<BookDTO> searchByFullCoincidence(Model model, @RequestBody ParametersDTO parametersDTO) {
        return bookService.getBookByFullCoincidence(parametersDTO);
    }

    @GetMapping(value = "/filter", headers = {"Accept=application/json"})
    public List<BookDTO> filterByDate(Model model, @RequestBody ParametersDTO parametersDTO)  {
        return  bookService.filter(parametersDTO);
    }
}
