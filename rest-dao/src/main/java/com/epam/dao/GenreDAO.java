package com.epam.dao;

import com.epam.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDAO {

    Long createGenreAndReturnId(String genreName);

    void removeGenre(long genreId);

    Optional<List<Genre>> getGenreList(int limit,int offset);

    Genre getGenreById(long genreId);

    Genre createGenre(String genreName);

    Genre getGenreByNameWithoutException(String genreName);

    Genre updateGenre(String genreName,long genreId);

    Genre getGenreByIdWithoutException(long genreId);
}
