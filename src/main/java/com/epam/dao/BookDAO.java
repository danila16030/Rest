package com.epam.dao;

import com.epam.entyty.Book;

import javax.sql.DataSource;
import java.util.List;

public interface BookDAO extends SetData {

    void createNewBook(String author, String description, int price, String writingDate, int numberOfPages, String title);

    void removeBook(String bookName);

    List<Book> getBookList();

    void updateBook(String newTitle, String author, String writingDate, String description, int numberOfPages, float price, String title);

    Book getBookByName(String bookName);

    Book findBookById(int id);

}
