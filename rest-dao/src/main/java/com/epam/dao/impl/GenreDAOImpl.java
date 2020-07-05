package com.epam.dao.impl;

import com.epam.dao.GenreDAO;
import com.epam.dao.impl.fields.GenreFields;
import com.epam.entity.Genre;
import com.epam.exception.DuplicatedException;
import com.epam.exception.NoSuchElementException;
import com.epam.rowMapper.GenreMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Repository
public class GenreDAOImpl implements GenreDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String createNewGenre = "INSERT INTO genre (genre_name) VALUES (?)";
    private static final String getGenreList = "SELECT * FROM genre LIMIT ? OFFSET ?";
    private static final String removeGenre = "DELETE FROM genre WHERE genre_id = ?";
    private static final String findGenreByName = "SELECT * FROM genre WHERE genre_name = ?";
    private static final String findGenreById = "SELECT * FROM genre WHERE genre_id = ?";
    private static final String updateGenre = "UPDATE genre SET genre_name = ? WHERE genre_id = ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long createGenreAndReturnId(String genreName) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(createNewGenre, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, genreName);
                return statement;
            }, keyHolder);
            Map<String, Object> keys = keyHolder.getKeys();
            return Long.parseLong(String.valueOf(keys.get(GenreFields.ID)));
        } catch (DuplicateKeyException e) {
            return 0l;
        }
    }


    @Override
    public boolean removeGenre(long genreId) {
        if (jdbcTemplate.update(removeGenre, genreId) < 1) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<List<Genre>> getGenreList(int limit,int offset) {
        try {
            return Optional.of(jdbcTemplate.query(getGenreList, new Object[]{limit,offset}, new GenreMapper()));
        } catch (
                EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }


    @Override
    public Genre getGenreByName(String genreName) {
        try {
            return jdbcTemplate.queryForObject(findGenreByName, new Object[]{genreName}, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Genre with this name does not exist");
        }
    }

    @Override
    public Genre createGenre(String genreName) {
        try {
            jdbcTemplate.update(createNewGenre, genreName);
            return new Genre(genreName);
        } catch (DuplicateKeyException e) {
            throw new DuplicatedException("Genre with this name is already exist");
        }
    }

    @Override
    public Genre getGenreByNameWithoutException(String genreName) {
        try {
            return jdbcTemplate.queryForObject(findGenreByName, new Object[]{genreName}, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            return new Genre();
        }
    }

    @Override
    public Genre updateGenre(String genreName, long genreId) {
        try {
            jdbcTemplate.update(updateGenre, genreName, genreId);
            return new Genre(genreName, genreId);
        } catch (DuplicateKeyException e) {
            throw new DuplicatedException("Genre with this name is already exist");
        }
    }

    @Override
    public Genre getGenreByIdWithoutException(long genreId) {
        try {
            return jdbcTemplate.queryForObject(findGenreById, new Object[]{genreId}, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            return new Genre();
        }
    }
}
