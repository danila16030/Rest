package com.epam.service;

import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.exception.InvalidDataException;

import java.util.List;

public interface GenreService {
    List<GenreResponseDTO> getAllGenres(int limit,int offset);

    GenreResponseDTO getGenre(String genreName);

    GenreResponseDTO updateGenre(UpdateGenreRequestDTO genreDTO);

    GenreResponseDTO createGenre(CreateGenreRequestDTO genreDTO) throws InvalidDataException;

    boolean removeGenre(long genreId) throws InvalidDataException;


}
