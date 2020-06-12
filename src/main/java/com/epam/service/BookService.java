package com.epam.service;

import com.epam.dto.BookDTO;
import com.epam.entyty.Book;

import java.util.List;

public interface BookService {
    public List<BookDTO> getAllBooks();
    public BookDTO getBook(String bookName);
    boolean removeBook(String bookName);
    int createBook(BookDTO book);
    boolean updateBook(BookDTO book);
}
