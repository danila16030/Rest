package com.epam.service.impl;

import com.epam.dao.GenreDAO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateGenreRequestDTO;
import com.epam.entity.Genre;
import com.epam.exception.DuplicatedException;
import com.epam.exception.InvalidDataException;
import com.epam.exception.NoSuchElementException;
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

    /**
     * Returns an list of genres.
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * <p>
     * This method return list of genres from database
     *
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list that contain genres from database
     * @see Genre
     */
    @Override
    public List<Genre> getAllGenres(int limit, int offset) {
        return genreDAO.getGenreList(limit, offset).get();
    }

    /**
     * Returns an Genre object by genre id
     * The genreId argument specify genre in database
     * <p>
     * This method return genre by id. If genre doesn't exist throw exception NoSuchElementException
     *
     * @param genreId specified genre id
     * @return the genre at the specified id
     * @throws NoSuchElementException
     * @see Genre
     */
    @Override
    public Genre getGenre(long genreId) {
        return genreDAO.getGenreById(genreId);
    }

    /**
     * Returns the updated genre
     * The genreDTO argument must contain information about the genre being updated.
     * <p>
     * This method return genre that was updated.If genre doesn't exist throw exception NoSuchElementException.
     * If genre with this name already exist throw DuplicatedException
     *
     * @param genreDTO object that contain information to be used when updating
     * @return the genre that was updated
     * @throws NoSuchElementException
     * @throws DuplicatedException
     * @see Genre
     * @see UpdateGenreRequestDTO
     */
    @Override
    public Genre updateGenre(UpdateGenreRequestDTO genreDTO) {
        if (!genreValidator.isExistByName(genreDTO.getGenreName())) {
            return genreDAO.updateGenre(mapper.genreDTOtoGenre(genreDTO));
        }
        throw new DuplicatedException("Genre with this name is already exist");
    }

    /**
     * Returns the created genre
     * The genreDTO argument must contain information about the genre being created.
     * <p>
     * This method return genre that was created. If genre with this name already exist throw DuplicatedException.
     *
     * @param genreDTO object that contain information to be used when creating a genre
     * @return the genre that was created
     * @throws DuplicatedException
     * @throws InvalidDataException
     * @see Genre
     * @see CreateGenreRequestDTO
     */
    @Override
    public Genre createGenre(CreateGenreRequestDTO genreDTO) {
        if (genreDTO != null) {
            return genreDAO.createGenre(mapper.genreDTOtoGenre(genreDTO));
        }
        throw new InvalidDataException();
    }

    /**
     * Remove genre from database.
     * The genreId argument specify genre in database
     * <p>
     * This method remove specified genre from database.
     * If genre doesn't exist throw exception NoSuchElementException
     *
     * @param genreId specified genre id
     * @throws NoSuchElementException
     */
    @Override
    public void removeGenre(long genreId) {
        if (genreValidator.isExistById(genreId)) {
            genreDAO.removeGenre(genreId);
        } else {
            throw new NoSuchElementException();
        }
    }
}
