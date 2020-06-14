package com.epam.dao;

import com.epam.entyty.Genre;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface GenreDAO extends SetData {

    public boolean createGenre(String genreName);

    public boolean removeGenre(String genreName);

    public Optional<List<Genre>> getGenreList();

    public Genre getGenreByName(String genreName);


}
