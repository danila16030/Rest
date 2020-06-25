package com.epam.daos.dao;

import com.epam.models.dto.ParametersDTO;
import com.epam.models.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO extends SetData {

    Long createNewBook(String author, String description, float price, String writingDate, int numberOfPages, String title);

    boolean removeBook(long bookId);

    Optional<List<Book>> getAllBooks();

    Book updateBook(String title, String author, String writingDate, String description, int numberOfPages, float price, long bookId);

    Optional<List<Book>> searchByPartialCoincidence(ParametersDTO parameters);

    Optional<List<Book>> searchByFullCoincidence(ParametersDTO parameters);

    Optional<List<Book>> filter(ParametersDTO parameters);

    Book getBookById(long bookId);


}
