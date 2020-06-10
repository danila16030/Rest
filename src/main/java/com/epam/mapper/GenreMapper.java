package com.epam.mapper;


import com.epam.dao.impl.fields.GenreFields;
import com.epam.dto.GenreDTO;
import com.epam.entyty.Genre;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Component
public class GenreMapper implements RowMapper<Genre> {
    @Autowired
    private ModelMapper mapper;

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setGenreName(resultSet.getString(GenreFields.GENRENAME));
        genre.setGenreId(resultSet.getInt(GenreFields.ID));
        return genre;
    }

    public Genre toEntity(GenreDTO dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Genre.class);
    }

    public GenreDTO toDto(Genre entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, GenreDTO.class);
    }
}
