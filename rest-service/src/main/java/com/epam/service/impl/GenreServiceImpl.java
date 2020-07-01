package com.epam.service.impl;

import com.epam.dao.GenreDAO;
import com.epam.dto.request.CreateGenreRequestDTO;
import com.epam.dto.request.UpdateGenreRequestDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import com.epam.mapper.BookGenreMapper;
import com.epam.service.GenreService;
import com.epam.validator.GenreValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDAO genreDAO;

    private GenreValidator genreValidator;
    private final BookGenreMapper genreMapper = Mappers.getMapper(BookGenreMapper.class);

    @Autowired
    public GenreServiceImpl(GenreDAO genreDAO, GenreValidator genreValidator) {
        this.genreDAO = genreDAO;
        this.genreValidator = genreValidator;
    }

    @Override
    public List<GenreResponseDTO> getAllGenres() {
        List<Genre> genreList = genreDAO.getGenreList().get();
        return genreMapper.genreListToGenreDTOList(genreList);
    }

    @Override
    public GenreResponseDTO getGenre(String genreName) {
        Genre genre = genreDAO.getGenreByName(genreName);
        return genreMapper.genreToGenreDTO(genre);
    }

    @Override
    public GenreResponseDTO updateGenre(UpdateGenreRequestDTO genreDTO) {
        if (genreDTO != null && genreValidator.isExistByName(genreDTO.getGenreName())) {
            Genre genre = genreDAO.updateGenre(genreDTO.getGenreName(), genreDTO.getGenreId());
            return genreMapper.genreToGenreDTO(genre);
        }
        throw new InvalidDataException();
    }


    @Override
    public GenreResponseDTO createGenre(CreateGenreRequestDTO genreDTO) {
        if (genreDTO != null && genreValidator.isValid(genreDTO) &&
                !genreValidator.isExistByName(genreDTO.getGenreName())) {
            Genre genre = genreDAO.createGenre(genreDTO.getGenreName());
            return genreMapper.genreToGenreDTO(genre);
        }
        throw new InvalidDataException();
    }

    @Override
    public boolean removeGenre(long genreId) {
        if (genreValidator.isExistById(genreId)) {
            return genreDAO.removeGenre(genreId);
        }
        throw new InvalidDataException();
    }
}
