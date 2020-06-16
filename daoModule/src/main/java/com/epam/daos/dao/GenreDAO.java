package com.epam.daos.dao;

import com.epam.entytys.entyty.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDAO extends SetData {

    public boolean createGenre(String genreName);

    public boolean removeGenre(String genreName);

    public Optional<List<Genre>> getGenreList();

    public Genre getGenreByName(String genreName);


}
