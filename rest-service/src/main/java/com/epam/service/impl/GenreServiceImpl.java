package com.epam.service.impl;

import com.epam.dao.GenreDAO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Genre;
import com.epam.exception.DuplicatedException;
import com.epam.exception.InvalidDataException;
import com.epam.mapper.Mapper;
import com.epam.service.GenreService;
import com.epam.validator.GenreValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {

    private GenreDAO genreDAO;
    private GenreValidator genreValidator;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    @Autowired
    public GenreServiceImpl(GenreDAO genreDAO, GenreValidator genreValidator) {
        this.genreDAO = genreDAO;
        this.genreValidator = genreValidator;
    }

    @Override
    public List<Genre> getAllGenres(int limit, int offset) {
        return genreDAO.getGenreList(limit, offset).get();
    }

    @Override
    public Genre getGenre(long genreId) {
        return genreDAO.getGenreById(genreId);
    }

    @Override
    public Genre updateGenre(UpdateGenreRequestDTO genreDTO) {
        if (!genreValidator.isExistByName(genreDTO.getGenreName())) {
            return genreDAO.updateGenre(mapper.genreDTOtoGenre(genreDTO));
        }
        throw new DuplicatedException("Genre with this name is already exist");
    }


    @Override
    public Genre createGenre(CreateGenreRequestDTO genreDTO) {
        if (genreDTO != null) {
            return genreDAO.createGenre(mapper.genreDTOtoGenre(genreDTO));
        }
        throw new InvalidDataException();
    }

    @Override
    public void removeGenre(long genreId) {
        if (genreValidator.isExistById(genreId)) {
            genreDAO.removeGenre(genreId);
        } else {
            throw new InvalidDataException();
        }
    }
}
