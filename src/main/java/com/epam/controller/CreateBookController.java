package com.epam.controller;

import com.epam.dto.BookDTO;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "book")
public class CreateBookController {
    @GetMapping("/view")
    public BookDTO createNewBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("films");
        System.out.println("ffff");
        return bookDTO;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView createNewBook2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit");
        System.out.println("ffff");
        return modelAndView;
    }
}
