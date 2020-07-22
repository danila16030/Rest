package com.epam.validator;

import com.epam.dao.GenreDAO;
import com.epam.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreValidator {
    @Autowired
    private GenreDAO genreDAO;

    public boolean isExistById(long genreId) {
        Genre genre = genreDAO.getGenreByIdWithoutException(genreId);
        if (genre == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isExistByName(String genreName) {
        Genre genre = genreDAO.getGenreByNameWithoutException(genreName);
        if (genre.getGenreName() == null) {
            return false;
        } else {
            return true;
        }
    }

}
