package com.epam.service;

import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface BookService {
    Book getBook(long bookId);


    Book changeBookPrice(ParametersRequestDTO parameters);

    List<Book> getAllBooks(int limit, int offset);

    List<Book> getBookByPartialCoincidence(ParametersRequestDTO parameters, int limit, int offset)
            throws InvalidDataException;

    List<Book> getBookByFullCoincidence(ParametersRequestDTO parameters, int limit, int offset)
            throws InvalidDataException;

    boolean removeBook(long bookId) throws InvalidDataException;

    Book createBook(CreateBookRequestDTO book) throws InvalidDataException;

    Book updateBook(UpdateBookRequestDTO book) throws InvalidDataException;

    List<Book> filter(ParametersRequestDTO parameters, int limit, int offset) throws InvalidDataException;

    List<Book> getBooksSortedByName(int limit, int offset);

    List<Book> getBooksSortedByDate(int limit, int offset);


}
