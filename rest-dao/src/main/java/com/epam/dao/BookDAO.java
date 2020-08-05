package com.epam.dao;

import com.epam.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO {

    Book createNewBook(Book book);

    void removeBook(long bookId);

    Optional<List<Book>> getAllBooks(int limit, int offset);

    Book updateBook(Book book);

    Optional<List<Book>> searchByPartialCoincidence(String title, int limit, int offset);

    Optional<List<Book>> searchByFullCoincidence(String title, int limit, int offset);

    Book getBookById(long bookId);

    Book getBookByIdWithoutException(long bookId);

    Optional<List<Book>> getBookSortedByAuthor(int limit, int offset);

}
