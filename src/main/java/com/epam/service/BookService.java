package com.epam.service;

import com.epam.dto.BookDTO;
import com.epam.dto.ParametersDTO;
import com.epam.entyty.Book;

import java.util.List;

public interface BookService {
    public List<BookDTO> getAllBooks();
    public List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters);
    public List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters);
    boolean removeBook(String bookName);
    int createBook(BookDTO book);
    boolean updateBook(BookDTO book);
    List<Book> filter(ParametersDTO parameters);
}
