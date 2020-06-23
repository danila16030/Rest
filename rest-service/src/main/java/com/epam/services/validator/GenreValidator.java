package com.epam.services.validator;

import com.epam.daos.dao.impl.GenreDAOImpl;
import com.epam.models.dto.GenreDTO;
import com.epam.models.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreValidator {
    @Autowired
    private GenreDAOImpl genreDAO;

    public boolean isExistById(GenreDTO genreDTO) {
        Genre genre = genreDAO.getGenreById(genreDTO.getGenreId());
        if (genre.getGenreName() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isExistByName(GenreDTO genreDTO) {
        Genre genre = genreDAO.getGenreByName(genreDTO.getGenreName());
        if (genre.getGenreName() == null) {
            return false;
        } else {
            return true;
        }
    }
}
