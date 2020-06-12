package com.epam.dao.impl;

import com.epam.dao.BookDAO;
import com.epam.entyty.Book;
import com.epam.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookDAOImpl implements BookDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String createNewBook = "INSERT INTO book (author,description,price,writing_date,page_number," +
            "title) VALUES(?,?,?,?,?,?)";
    private static final String findBookByPartialName = "SELECT * FROM book WHERE title LIKE('%' || ? || '%')";
    private static final String findBookByFullName = "SELECT * FROM book WHERE title = ?";
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
    public int createNewBook(String author, String description, float price, String writingDate, int numberOfPages,
                             String title) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(createNewBook, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, author);
                statement.setString(2, description);
                statement.setFloat(3, price);
                statement.setString(4, writingDate);
                statement.setInt(5, numberOfPages);
                statement.setString(6, title);
                return statement;
            }, keyHolder);
            Map<String, Object> keys = keyHolder.getKeys();
            return (int) keys.get("book_id");
        } catch (DuplicateKeyException e) {
            return 0;
        }
    }

    @Override
    public boolean removeBook(String bookName) {
        if (jdbcTemplate.update(removeBook, bookName) < 1) {
            return false;
        }
        return true;
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
    public boolean updateBook(String newTitle, String author, String writingDate, String description, int numberOfPages,
                              float price, String title) {
        if (jdbcTemplate.update(updateBook, newTitle, author, writingDate, description, numberOfPages, price, title) < 1) {
            return false;
        }
        return true;
    }

    @Override
    public Book getBookByPartialCoincidence(String value) {
        try {
            return jdbcTemplate.queryForObject(findBookByPartialName, new Object[]{value}, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book getBookByFullCoincidence(String value) {
        try {
            return jdbcTemplate.queryForObject(findBookByFullName, new Object[]{value}, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
