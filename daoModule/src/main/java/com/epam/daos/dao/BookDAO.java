package com.epam.daos.dao;

import com.epam.entytys.dto.ParametersDTO;
import com.epam.entytys.entyty.Book;

import java.util.List;
import java.util.Optional;

public interface BookDAO extends SetData {

    int createNewBook(String author, String description, float price, String writingDate, int numberOfPages, String title);

    boolean removeBook(String bookName);

    Optional<List<Book>> getBookList();

    boolean updateBook(String newTitle, String author, String writingDate, String description, int numberOfPages, float price, String title);

    Optional<List<Book>> searchByPartialCoincidence(ParametersDTO parameters);

    Optional<List<Book>> searchByFullCoincidence(ParametersDTO parameters);

    Optional<List<Book>> filter(ParametersDTO parameters);

    Book getBookByName(String name);


}
