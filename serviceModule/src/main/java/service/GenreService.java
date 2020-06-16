package service;

import dto.GenreDTO;
import entyty.Genre;

import java.util.List;

public interface GenreService {
    public List<GenreDTO> getAllGenres();

    public GenreDTO getGenre(String genreName);

    public int getGenreId(String genreName);

    boolean createGenre(GenreDTO genre);

    boolean removeGenre(String genreName);


}
