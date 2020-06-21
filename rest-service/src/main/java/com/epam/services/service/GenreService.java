package com.epam.services.service;

import com.epam.models.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    public List<GenreDTO> getAllGenres();

    public GenreDTO getGenre(String genreName);


    boolean createGenre(GenreDTO genreDTO);

    boolean removeGenre(GenreDTO genreDTO );


}
