package com.epam.dao.impl;

import com.epam.dao.BookDAO;
import com.epam.entyty.Book;
import com.epam.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.Configuration;
import javax.sql.DataSource;
import java.util.List;


@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookDAOImpl implements BookDAO {
    private JdbcTemplate jdbcTemplate;
    private String sqlCreateNewBook = "INSERT INTO book (author,description,price,writing_date,page_number,title) " +
            "VALUES(?,?,?,?,?,?)";
    private String sqlFindBook = "SELECT * FROM book WHERE title = ?";
    private String sqlRemoveBook = "DELETE FROM book WHERE title = ?";
    private String sqlGetBookList = "SELECT * FROM book";
    private String sqlUpdateBook = "UPDATE book SET title = ?, author = ?, writing_date = ? ,description=? " +
            ",page_number=? ,price=? WHERE title = ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createNewBook(String author, String description, int price, String writingDate, int numberOfPages,
                              String title) {
        jdbcTemplate.update(sqlCreateNewBook, author, description, price, writingDate, numberOfPages, title);
    }

    @Override
    public void removeBook(String bookName) {
        jdbcTemplate.update(sqlRemoveBook, bookName);
    }

    @Override
    public List<Book> getBookList() {
        try {
            return jdbcTemplate.query(sqlGetBookList, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateBook(String newTitle, String author, String writingDate, String description, int numberOfPages, float price, String title) {
        jdbcTemplate.update(sqlUpdateBook, newTitle, author, writingDate, description, numberOfPages, price, title);
    }


    @Override
    public Book findBook(String bookName) {
        try {
            return jdbcTemplate.queryForObject(sqlFindBook, new Object[]{bookName}, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
