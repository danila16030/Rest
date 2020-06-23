package com.epam.services.service;

import com.epam.models.dto.BookDTO;
import com.epam.models.dto.ParametersDTO;

import java.util.List;

public interface BookService {

    BookDTO getBook(long bookId);

    List<BookDTO> getAllBooks();

    List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters);

    List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters);

    boolean removeBook(BookDTO book);

    boolean createBook(BookDTO book);

    boolean updateBook(BookDTO book);

    List<BookDTO> filter(ParametersDTO parameters);

    List<BookDTO> getBooksSortedByName();

    List<BookDTO> getBooksSortedByDate();

}
