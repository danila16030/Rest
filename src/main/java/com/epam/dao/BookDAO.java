package com.epam.dao;

import com.epam.entyty.Book;

import java.util.List;

public interface BookDAO extends SetData {

    int createNewBook(String author, String description, float price, String writingDate, int numberOfPages, String title);

    boolean removeBook(String bookName);

    List<Book> getBookList();

    boolean updateBook(String newTitle, String author, String writingDate, String description, int numberOfPages, float price, String title);

    Book getBookByName(String bookName);


}
