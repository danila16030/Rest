package com.epam.dao;

import com.epam.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDAO {

    Long createGenreAndReturnId(Genre genre);

    void removeGenre(long genreId);

    Optional<List<Genre>> getGenreList(int limit, int offset);

    Genre getGenreById(long genreId);

    Genre createGenre(Genre genre);

    Genre getGenreByNameWithoutException(String genreName);

    Genre updateGenre(Genre genre);

    Genre getGenreByIdWithoutException(long genreId);
}
