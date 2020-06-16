package service.impl;

import dao.impl.GenreDAOImpl;
import dto.GenreDTO;
import entyty.Genre;
import mapper.BookGenreMapper;
import service.GenreService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDAOImpl genreDAO;
    private BookGenreMapper bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);

    @Autowired
    public GenreServiceImpl(GenreDAOImpl dao) {
        this.genreDAO = dao;
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
    public int getGenreId(String genreName) {
        Genre genre = genreDAO.getGenreByName(genreName);
        if (genre == null) {
            genreDAO.createGenre(genreName);
            return genreDAO.getGenreByName(genreName).getGenreId();
        } else {
            return genre.getGenreId();
        }
    }

    @Override
    public boolean createGenre(GenreDTO genre) {
        return genreDAO.createGenre(genre.getGenreName());
    }

    @Override
    public boolean removeGenre(String genreName) {
        return genreDAO.removeGenre(genreName);
    }
}