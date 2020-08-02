package com.epam.service;

import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface BookService {
    Book getBook(long bookId);

    List<Book> getAllBooks(int limit, int offset);

    List<Book> getBookByPartialCoincidence(String title, int limit, int offset);

    List<Book> getBookByFullCoincidence(String title, int limit, int offset);

    void removeBook(long bookId) throws InvalidDataException;

    Book createBook(CreateBookRequestDTO book) throws InvalidDataException;

    Book updateBook(UpdateBookRequestDTO book) throws InvalidDataException;

    Genre geTheMostCommonGenre(long fist, long second, long third);

    List<Book> getBooksSortedByName(int limit, int offset);
}
