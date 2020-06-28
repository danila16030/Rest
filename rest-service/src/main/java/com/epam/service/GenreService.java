package com.epam.service;

import com.epam.dto.GenreDTO;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface GenreService {
    public List<GenreDTO> getAllGenres();

    public GenreDTO getGenre(String genreName);


    GenreDTO createGenre(GenreDTO genreDTO) throws InvalidDataException;

    boolean removeGenre(GenreDTO genreDTO) throws InvalidDataException;


}
