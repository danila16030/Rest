package com.epam.service;

import com.epam.dto.request.CreateGenreRequestDTO;
import com.epam.dto.request.UpdateGenreRequestDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface GenreService {
    List<GenreResponseDTO> getAllGenres();

    GenreResponseDTO getGenre(String genreName);

    GenreResponseDTO updateGenre(UpdateGenreRequestDTO genreDTO);

    GenreResponseDTO createGenre(CreateGenreRequestDTO genreDTO) throws InvalidDataException;

    boolean removeGenre(long genreId) throws InvalidDataException;


}
