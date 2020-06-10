package com.epam.dao.impl;

import com.epam.dao.BookDAO;
import com.epam.entyty.Book;
import com.epam.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookDAOImpl implements BookDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String createNewBook = "INSERT INTO book (author,description,price,writing_date,page_number," +
            "title) VALUES(?,?,?,?,?,?)";
    private static final String findBookByName = "SELECT * FROM book WHERE title = ?";
    private static final String findBookById = "SELECT * FROM book WHERE id = ?";
    private static final String removeBook = "DELETE FROM book WHERE title = ?";
    private static final String getBookList = "SELECT * FROM book";
    private static final String updateBook = "UPDATE book SET title = ?, author = ?, writing_date = ? ,description=? " +
            ",page_number=? ,price=? WHERE title = ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createNewBook(String author, String description, int price, String writingDate, int numberOfPages,
                              String title) {
        jdbcTemplate.update(createNewBook, author, description, price, writingDate, numberOfPages, title);
    }

    @Override
    public void removeBook(String bookName) {
        jdbcTemplate.update(removeBook, bookName);
    }

    @Override
    public List<Book> getBookList() {
        try {
            return jdbcTemplate.query(getBookList, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateBook(String newTitle, String author, String writingDate, String description, int numberOfPages,
                           float price, String title) {
        jdbcTemplate.update(updateBook, newTitle, author, writingDate, description, numberOfPages, price, title);
    }


    @Override
    public Book getBookByName(String bookName) {
        try {
            return jdbcTemplate.queryForObject(findBookByName, new Object[]{bookName}, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book findBookById(int id) {
        try {
            return jdbcTemplate.queryForObject(findBookById, new Object[]{id}, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
