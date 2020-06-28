package com.epam.mapper;


import com.epam.dto.GenreDTO;
import com.epam.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {
    GenreMapper GENRE_MAPPER = Mappers.getMapper(GenreMapper.class);

    GenreDTO genreToGenreDTO(Genre genre);

    List<GenreDTO> genreListToGenreDTOList(List<Genre> genres);
}
