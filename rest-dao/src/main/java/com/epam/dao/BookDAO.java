package com.epam.dao;

import com.epam.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    Long createNewBook(String author, String description, float price, String writingDate, int numberOfPages, String title);

    void removeBook(long bookId);

    Optional<List<Book>> getAllBooks(int limit,int offset);

    Book updateBook(String title, String author, String writingDate, String description, int numberOfPages, float price, long bookId);

    Optional<List<Book>> searchByPartialCoincidence(String title,int limit,int offset);

    Optional<List<Book>> searchByFullCoincidence(String title,int limit,int offset);

    Book getBookById(long bookId);


    Book getBookByIdWithoutException(long bookId);


}
