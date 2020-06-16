package com.epam.services.service;

import com.epam.entytys.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    public List<GenreDTO> getAllGenres();

    public GenreDTO getGenre(String genreName);

    public int getGenreId(String genreName);

    boolean createGenre(GenreDTO genre);

    boolean removeGenre(String genreName);


}
