package com.epam.mapper;


import com.epam.dao.impl.fields.GenreFields;
import com.epam.entyty.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setGenreName(resultSet.getString(GenreFields.GENRENAME));
        genre.setGenreId(resultSet.getInt(GenreFields.ID));
        return genre;
    }
}
