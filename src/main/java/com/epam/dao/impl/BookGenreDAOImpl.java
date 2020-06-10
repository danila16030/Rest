package com.epam.dao.impl;

import com.epam.dao.BookGenreDAO;
import com.epam.entyty.Book;
import com.epam.entyty.Genre;
import com.epam.mapper.BookMapper;
import com.epam.mapper.GenreMapper;
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
public class BookGenreDAOImpl implements BookGenreDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String getAllBooksByGenre = "SELECT * FROM book_genre sc INNER JOIN book c ON  +" +
            "c.book_id=sc.book_id WHERE sc.genre_id=?";
    private static final String getAllGenresOnBook = "SELECT * FROM book_genre sc INNER JOIN genre c ON " +
            "c.genre_id=sc.genre_id WHERE sc.book_id=?";
    private static final String createNew = "INSERT INTO book_genre(book_id, genre_id) VALUES (?,?)";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Book> getAllBooksByGenre(int genreId) {
        try {
            return jdbcTemplate.query(getAllBooksByGenre, new Object[]{genreId}, new BookMapper());
        } catch (
                EmptyResultDataAccessException e) {
            return null;//optional
        }
    }

    @Override
    public List<Genre> getAllGenresOnBook(int bookId) {
        try {
            return jdbcTemplate.query(getAllGenresOnBook, new Object[]{bookId}, new GenreMapper());
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void createNew(int bookId, int genreId) {
        jdbcTemplate.update(createNew, bookId, genreId);
    }

}
