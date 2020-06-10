package com.epam.dao;

import com.epam.entyty.Genre;

import javax.sql.DataSource;
import java.util.List;

public interface GenreDAO extends SetData {

    public void createGenre(String genreName);

    public void removeGenre(String genreName);

    public List<Genre> getGenreList();
}
