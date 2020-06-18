package com.epam.services.service;

import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.dto.ParametersDTO;
import com.epam.entytys.entyty.Book;

import java.util.List;

public interface BookService {

    BookDTO getBookByName(String bookName);

    List<BookDTO> getAllBooks();

    List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters);

    List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters);

    boolean removeBook(String bookName);

    int createBook(BookDTO book);

    boolean updateBook(BookDTO book);

    List<BookDTO> filter(ParametersDTO parameters);

    List<BookDTO> getBooksSortedByName();

    List<BookDTO> getBooksSortedByDate();

}
