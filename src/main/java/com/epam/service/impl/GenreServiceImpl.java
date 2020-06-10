package com.epam.service.impl;

import com.epam.dao.impl.GenreDAOImpl;
import com.epam.dto.GenreDTO;
import com.epam.entyty.Genre;
import com.epam.mapper.GenreMapper;
import com.epam.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreDAOImpl genreDAO;
    private GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(GenreDAOImpl dao, GenreMapper mapper) {
        this.genreDAO = dao;
        this.genreMapper = mapper;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        List<Genre> genreList = genreDAO.getGenreList();
        ArrayList<GenreDTO> dtoList = new ArrayList<>();
        for (Genre genre : genreList) {
            dtoList.add(genreMapper.toDto(genre));
        }
        return dtoList;
    }

    @Override
    public GenreDTO getGenre(String genreName) {
        Genre genre = genreDAO.getGenreByName(genreName);
        return genreMapper.toDto(genre);
    }
}
