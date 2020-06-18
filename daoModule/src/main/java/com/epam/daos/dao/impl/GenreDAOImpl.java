package com.epam.daos.dao.impl;

import com.epam.daos.rowMapper.GenreMapper;
import com.epam.daos.dao.GenreDAO;
import com.epam.entytys.entyty.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GenreDAOImpl implements GenreDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String createNewGenre = "INSERT INTO genre (genre_name) VALUES (?)";
    private static final String getGenreList = "SELECT * FROM genre";
    private static final String removeGenre = "DELETE FROM genre WHERE genre_name = ?";
    private static final String findGenreByName = "SELECT * FROM genre WHERE genre_name = ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean createGenre(String genreName) {
        try {
            jdbcTemplate.update(createNewGenre, genreName);
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public boolean removeGenre(String genreName) {
        if (jdbcTemplate.update(removeGenre, genreName) < 1) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<List<Genre>> getGenreList() {
        try {
            return Optional.of(jdbcTemplate.query(getGenreList, new GenreMapper()));
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Genre getGenreByName(String genreName) {
        try {
            return jdbcTemplate.queryForObject(findGenreByName, new Object[]{genreName}, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            return new Genre();
        }
    }
}
