package com.epam.rowMapper;


import com.epam.dao.impl.fields.GenreFields;
import com.epam.entity.Genre;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre(resultSet.getString(GenreFields.GENRENAME));
        genre.setGenreId(resultSet.getInt(GenreFields.ID));
        return genre;
    }
}
