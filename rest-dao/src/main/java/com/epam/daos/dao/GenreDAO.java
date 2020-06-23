package com.epam.daos.dao;

import com.epam.models.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDAO extends SetData {

    Long createGenreAndReturnId(String genreName);

    boolean removeGenre(long genreId);

    Optional<List<Genre>> getGenreList();

    Genre getGenreByName(String genreName);

    Genre getGenreById(long genreId);

    boolean createGenre(String genreName);

}
