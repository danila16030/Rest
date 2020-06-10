package com.epam.dao.impl;

import com.epam.dao.GenreDAO;
import com.epam.entyty.Genre;
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
public class GenreDAOImpl implements GenreDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String createNewGenre = "INSERT INTO genre genre_name=?";
    private static final String getGenreList = "SELECT * FROM genre";
    private static final String removeGenre = "DELETE FROM genre WHERE genre_name = ?";
    private static final String findGenreByName = "SELECT * FROM genre WHERE genre_name = ?";
    private static final String findGenreById = "SELECT * FROM genre WHERE id = ?";
    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createGenre(String genreName) {
        jdbcTemplate.update(createNewGenre, genreName);
    }

    @Override
    public void removeGenre(String genreName) {
        jdbcTemplate.update(removeGenre, genreName);
    }

    @Override
    public List<Genre> getGenreList() {
        try {
            return jdbcTemplate.query(getGenreList, new GenreMapper());
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Genre getGenreByName(String genreName) {
        try {
            return jdbcTemplate.queryForObject(findGenreByName, new Object[]{genreName}, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Genre getGenreById(int id) {
        try {
            return jdbcTemplate.queryForObject(findGenreById, new Object[]{id}, new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
