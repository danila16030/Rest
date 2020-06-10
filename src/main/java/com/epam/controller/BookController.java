package com.epam.controller;

import com.epam.dto.BookDTO;
import com.epam.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;

    private BookDTO getEmployeesCollection() {
        BookDTO employees = new BookDTO();
        employees.setAuthor("FFFF");
        return employees;
    }

    @RequestMapping(value = "/getAllBooks")
    public String getAllBooksJSON(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "jsonTemplate";
    }
}
