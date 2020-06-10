package com.epam.service;

import com.epam.dto.BookDTO;
import com.epam.entyty.Book;

import java.util.List;

public interface BookService {
    public List<BookDTO> getAllBooks();
}
