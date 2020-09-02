package com.epam.service;

import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres(int limit, int offset);

    Genre getGenre(long genreId);

    Genre getGenre(String genreName);

    Genre updateGenre(UpdateGenreRequestDTO genreDTO);

    Genre createGenre(CreateGenreRequestDTO genreDTO) throws InvalidDataException;

    void removeGenre(long genreId) throws InvalidDataException;


}
