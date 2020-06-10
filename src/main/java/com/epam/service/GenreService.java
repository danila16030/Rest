package com.epam.service;

import com.epam.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    public List<GenreDTO> getAllGenres();
    public GenreDTO getGenre(String genreName);
}
