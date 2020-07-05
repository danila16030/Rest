package com.epam.dao.impl;

import com.epam.dao.BookDAO;
import com.epam.dao.impl.fields.BookFields;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.entity.Book;
import com.epam.exception.NoSuchElementException;
import com.epam.rowMapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class BookDAOImpl implements BookDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String createNewBook = "INSERT INTO book (author,description,price,writing_date,page_number," +
            "title) VALUES(?,?,?,?,?,?)";
    private static final String blank = "SELECT * FROM book WHERE";
    private static final String findBookById = "SELECT * FROM book WHERE book_id = ?";
    private static final String removeBook = "DELETE FROM book WHERE book_id = ?";
    private static final String getBookList = "SELECT * FROM book LIMIT ? OFFSET ?";
    private static final String changePrice = "UPDATE book SET price = ? WHERE book_id=?";
    private static final String updateBook = "UPDATE book SET title = ?, author = ?, writing_date = ? ,description=? " +
            ",page_number=? ,price=? WHERE book_id = ?";
    private static final String pagination = "LIMIT ? OFFSET ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long createNewBook(String author, String description, float price, String writingDate, int numberOfPages,
                              String title) {
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
        return Long.parseLong(String.valueOf(keys.get(BookFields.ID)));
    }

    @Override
    public boolean removeBook(long bookId) {
        jdbcTemplate.update(removeBook, bookId);
        return true;
    }

    @Override
    public Optional<List<Book>> getAllBooks(int limit, int offset) {
        try {
            return Optional.of(jdbcTemplate.query(getBookList, new Object[]{limit, offset}, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Book updateBook(String title, String author, String writingDate, String description, int numberOfPages,
                           float price, long bookId) {
        if (jdbcTemplate.update(updateBook, title, author, writingDate, description, numberOfPages, price, bookId) < 1) {
            throw new NoSuchElementException();
        }
        return new Book(author, description, price, writingDate, numberOfPages, title);
    }

    @Override
    public Optional<List<Book>> searchByPartialCoincidence(ParametersRequestDTO parameters, int limit, int offset) {
        final String[] newFilter = {blank};
        parameters.getParameters().forEach((k, v) -> newFilter[0] += " " + k + " LIKE '%" + v + "%'");
        String search = newFilter[0] + pagination;
        try {
            return Optional.of(jdbcTemplate.query(search, new Object[]{limit, offset}, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Book>> searchByFullCoincidence(ParametersRequestDTO parameters, int limit, int offset) {
        final String[] newFilter = {blank};
        parameters.getParameters().forEach((k, v) -> newFilter[0] += " " + k + " = " + "'" + v + "'");
        String search = newFilter[0] + pagination;
        try {
            return Optional.of(jdbcTemplate.query(search, new Object[]{limit, offset}, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    private String replace(String string, int number) {
        string = string.substring(0, string.length() - number);
        return string;
    }

    @Override
    public Optional<List<Book>> filter(ParametersRequestDTO parameters, int limit, int offset) {
        final String[] newFilter = {blank};
        parameters.getParameters().forEach((k, v) -> newFilter[0] += " " + k + " = " + "'" + v + "'" + " AND");
        String newFilterString = newFilter[0] + pagination;
        newFilterString = replace(newFilterString, 4);
        try {
            return Optional.of(jdbcTemplate.query(newFilterString, new Object[]{limit, offset}, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Book getBookById(long bookId) {
        try {
            return jdbcTemplate.queryForObject(findBookById, new Object[]{bookId}, new BookMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Book changeBookPrice(float price, long bookId) {
        try {
            jdbcTemplate.update(changePrice, price, bookId);
            return getBookById(bookId);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Book getBookByIdWithoutException(long bookId) {
        try {
            return jdbcTemplate.queryForObject(findBookById, new Object[]{bookId}, new BookMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            return new Book();
        }
    }
}

