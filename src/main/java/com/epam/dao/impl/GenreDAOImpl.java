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
    private String sqlCreateNewGenre = "INSERT INTO genre genre_name=?";
    private String sqlGetGenreList = "SELECT * FROM genre";
    private String sqlRemoveGenre = "DELETE FROM genre WHERE genre_name = ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createGenre(String genreName) {
        jdbcTemplate.update(sqlCreateNewGenre, genreName);
    }

    @Override
    public void removeGenre(String genreName) {
        jdbcTemplate.update(sqlRemoveGenre, genreName);
    }

    @Override
    public List<Genre> getGenreList() {
        try {
            return jdbcTemplate.query(sqlGetGenreList, new GenreMapper());
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }
}
