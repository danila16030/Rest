package com.epam.dao;

import com.epam.entyty.Genre;

import java.util.List;

public interface GenreDAO extends SetData {

    public boolean createGenre(String genreName);

    public boolean removeGenre(String genreName);

    public List<Genre> getGenreList();

    public Genre getGenreByName(String genreName);


}
