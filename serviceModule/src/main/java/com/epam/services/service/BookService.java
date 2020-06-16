package com.epam.services.service;

import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.dto.ParametersDTO;

import java.util.List;

public interface BookService {
    public List<BookDTO> getAllBooks();
    public List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters);
    public List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters);
    boolean removeBook(String bookName);
    int createBook(BookDTO book);
    boolean updateBook(BookDTO book);
    List<BookDTO> filter(ParametersDTO parameters);
    List<BookDTO> getBooksSortedByName();
    List<BookDTO> getBooksSortedByDate();

}
