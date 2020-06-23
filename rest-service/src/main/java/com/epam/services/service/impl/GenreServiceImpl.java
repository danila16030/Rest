package com.epam.services.service.impl;

import com.epam.daos.dao.impl.GenreDAOImpl;
import com.epam.models.dto.GenreDTO;
import com.epam.models.entity.Genre;
import com.epam.models.mapper.BookGenreMapper;
import com.epam.services.service.GenreService;
import com.epam.services.validator.GenreValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDAOImpl genreDAO;
    private BookGenreMapper bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);
    private GenreValidator genreValidator;

    @Autowired
    public GenreServiceImpl(GenreDAOImpl genreDAO, GenreValidator genreValidator) {
        this.genreDAO = genreDAO;
        this.genreValidator = genreValidator;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        List<Genre> genreList = genreDAO.getGenreList().get();
        return bookGenreMapper.genreListToGenreDTOList(genreList);
    }

    @Override
    public GenreDTO getGenre(String genreName) {
        Genre genre = genreDAO.getGenreByName(genreName);
        return bookGenreMapper.genreToGenreDTO(genre);
    }


    @Override
    public boolean createGenre(GenreDTO genreDTO) {
        if (genreDTO != null && !genreValidator.isExistByName(genreDTO)) {
            return genreDAO.createGenre(genreDTO.getGenreName());
        }
        return false;
    }

    @Override
    public boolean removeGenre(GenreDTO genreDTO) {
        if (genreDTO != null && genreValidator.isExistById(genreDTO)) {
            return genreDAO.removeGenre(genreDTO.getGenreId());
        }
        return false;
    }
}
