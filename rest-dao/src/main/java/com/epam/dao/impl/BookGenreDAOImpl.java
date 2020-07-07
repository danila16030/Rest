package com.epam.dao.impl;

import com.epam.dao.BookGenreDAO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.exception.NoSuchElementException;
import com.epam.rowMapper.BookMapper;
import com.epam.rowMapper.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BookGenreDAOImpl implements BookGenreDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String getAllBooksByGenre = "SELECT * FROM book_genre sc INNER JOIN book c ON  +" +
            "c.book_id=sc.book_id WHERE sc.genre_id=? LIMIT ? OFFSET ?";
    private static final String getAllGenresOnBook = "SELECT * FROM book_genre sc INNER JOIN genre c ON " +
            "c.genre_id=sc.genre_id WHERE sc.book_id=? LIMIT ? OFFSET ?";
    private static final String getAllGenresOnBookWithoutLimit = "SELECT * FROM book_genre sc INNER JOIN genre c ON " +
            "c.genre_id=sc.genre_id WHERE sc.book_id=?";
    private static final String createConnection = "INSERT INTO book_genre(book_id, genre_id) VALUES (?,?)";
    private static final String checkConnection = "SELECT count(*) FROM  book_genre WHERE book_id=? AND genre_id=?";
    private static final String getBookByGenres = "SELECT * FROM book_genre WHERE genre_id=? GROUP BY book_id";


    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<List<Book>> getAllBooksByGenre(long genreId, int limit, int offset) {
        try {
            return Optional.of(jdbcTemplate.query(getAllBooksByGenre, new Object[]{genreId, limit, offset}, new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Genre>> getAllGenresOnBook(long bookId, int limit, int offset) {
        try {
            return Optional.of(jdbcTemplate.query(getAllGenresOnBook, new Object[]{bookId, limit, offset}, new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Genre>> getAllGenresOnBook(long bookId) {
        try {
            return Optional.of(jdbcTemplate.query(getAllGenresOnBookWithoutLimit, new Object[]{bookId}, new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void createConnection(long bookId, long genreId) {
        jdbcTemplate.update(createConnection, bookId, genreId);
    }

    @Override
    public boolean checkConnection(long bookId, long genreId) {
        return jdbcTemplate.queryForObject(checkConnection, new Object[]{bookId, genreId}, Integer.class) > 0;
    }

    @Override
    public Book getBookByGenres(ParametersRequestDTO parametersRequestDTO) {
        try {
 //           return jdbcTemplate.queryForObject(getBookByGenres, new BookMapper());
            return null;
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

}
