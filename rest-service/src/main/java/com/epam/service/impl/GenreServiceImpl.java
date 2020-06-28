package com.epam.service.impl;

import com.epam.dao.impl.GenreDAOImpl;
import com.epam.dto.GenreDTO;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import com.epam.mapper.GenreMapper;
import com.epam.service.GenreService;
import com.epam.validator.GenreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDAOImpl genreDAO;

    private GenreValidator genreValidator;

    @Autowired
    public GenreServiceImpl(GenreDAOImpl genreDAO, GenreValidator genreValidator) {
        this.genreDAO = genreDAO;
        this.genreValidator = genreValidator;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        List<Genre> genreList = genreDAO.getGenreList().get();
        return  GenreMapper.GENRE_MAPPER.genreListToGenreDTOList(genreList);
    }

    @Override
    public GenreDTO getGenre(String genreName) {
        Genre genre = genreDAO.getGenreByName(genreName);
        return GenreMapper.GENRE_MAPPER.genreToGenreDTO(genre);
    }


    @Override
    public Genre createGenre(GenreDTO genreDTO)  {
        if (genreDTO != null && genreValidator.isValid(genreDTO) &&
                !genreValidator.isExistByName(genreDTO.getGenreName())) {
            return genreDAO.createGenre(genreDTO.getGenreName());
        }
        throw new InvalidDataException();
    }

    @Override
    public boolean removeGenre(GenreDTO genreDTO) {
        if (genreDTO != null && genreValidator.isExistById(genreDTO.getGenreId())) {
            return genreDAO.removeGenre(genreDTO.getGenreId());
        }
        throw new InvalidDataException();
    }
}
