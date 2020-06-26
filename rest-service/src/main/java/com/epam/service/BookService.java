package com.epam.service;

import com.epam.dto.BookDTO;
import com.epam.dto.ParametersDTO;
import com.epam.entity.Book;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface BookService {

    BookDTO getBook(long bookId);

    List<BookDTO> getAllBooks();

    List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters) throws InvalidDataException;

    List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters) throws InvalidDataException;

    boolean removeBook(BookDTO book) throws InvalidDataException;

    BookDTO createBook(BookDTO book) throws InvalidDataException;

    BookDTO updateBook(BookDTO book) throws InvalidDataException;

    List<BookDTO> filter(ParametersDTO parameters) throws InvalidDataException;

    List<BookDTO> getBooksSortedByName();

    List<BookDTO> getBooksSortedByDate();

}
