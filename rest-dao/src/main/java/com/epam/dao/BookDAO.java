package com.epam.dao;

import com.epam.dto.request.ParametersRequestDTO;
import com.epam.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO extends SetData {

    Long createNewBook(String author, String description, float price, String writingDate, int numberOfPages, String title);

    boolean removeBook(long bookId);

    Optional<List<Book>> getAllBooks(int limit,int offset);

    Book updateBook(String title, String author, String writingDate, String description, int numberOfPages, float price, long bookId);

    Optional<List<Book>> searchByPartialCoincidence(ParametersRequestDTO parameters,int limit,int offset);

    Optional<List<Book>> searchByFullCoincidence(ParametersRequestDTO parameters,int limit,int offset);

    Optional<List<Book>> filter(ParametersRequestDTO parameters,int limit,int offset);

    Book getBookById(long bookId);

    Book changeBookPrice(float price, long bookId);

    Book getBookByIdWithoutException(long bookId);


}
