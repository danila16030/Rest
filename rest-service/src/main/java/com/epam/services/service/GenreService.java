package com.epam.services.service;

import com.epam.models.dto.GenreDTO;
import com.epam.models.entity.Genre;

import java.util.List;

public interface GenreService {
    public List<GenreDTO> getAllGenres();

    public GenreDTO getGenre(String genreName);


    Genre createGenre(GenreDTO genreDTO);

    boolean removeGenre(GenreDTO genreDTO);


}
