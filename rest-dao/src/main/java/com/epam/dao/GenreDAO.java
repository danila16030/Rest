package com.epam.dao;

import com.epam.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDAO extends SetData {

    Long createGenreAndReturnId(String genreName);

    boolean removeGenre(long genreId);

    Optional<List<Genre>> getGenreList(int limit,int offset);

    Genre getGenreByName(String genreName);

    Genre createGenre(String genreName);

    Genre getGenreByNameWithoutException(String genreName);

    Genre updateGenre(String genreName,long genreId);

    Genre getGenreByIdWithoutException(long genreId);
}
