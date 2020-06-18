package com.epam.daos.dao.impl;

import com.epam.daos.rowMapper.BookMapper;
import com.epam.daos.rowMapper.GenreMapper;
import com.epam.daos.dao.BookGenreDAO;
import com.epam.entytys.entyty.Book;
import com.epam.entytys.entyty.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookGenreDAOImpl implements BookGenreDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String getAllBooksByGenre = "SELECT * FROM book_genre sc INNER JOIN book c ON  +" +
            "c.book_id=sc.book_id WHERE sc.genre_id=?";
    private static final String getAllGenresOnBook = "SELECT * FROM book_genre sc INNER JOIN genre c ON " +
            "c.genre_id=sc.genre_id WHERE sc.book_id=?";
    private static final String createConnection = "INSERT INTO book_genre(book_id, genre_id) VALUES (?,?)";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<List<Book>> getAllBooksByGenre(int genreId) {
        try {
            return Optional.of(jdbcTemplate.query(getAllBooksByGenre, new Object[]{genreId}, new BookMapper()));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Genre>> getAllGenresOnBook(int bookId) {
        try {
            return Optional.of(jdbcTemplate.query(getAllGenresOnBook, new Object[]{bookId}, new GenreMapper()));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void createConnection(int bookId, int genreId) {
        jdbcTemplate.update(createConnection, bookId, genreId);
    }

}
