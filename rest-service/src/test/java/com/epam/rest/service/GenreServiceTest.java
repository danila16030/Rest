package com.epam.rest.service;

import com.epam.exception.InvalidDataException;
import com.epam.rest.config.TestConfig;
import com.epam.dao.impl.GenreDAOImpl;
import com.epam.dto.GenreDTO;
import com.epam.entity.Genre;
import com.epam.service.GenreService;
import com.epam.validator.GenreValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class GenreServiceTest {
    @Mock
    private GenreDAOImpl genreDAO;

    @Autowired
    @InjectMocks
    GenreService genreService;


    @Autowired
    @Mock
    private GenreValidator genreValidator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getGenreByNameTest() {
        Genre expected = createGenre();
        expected.setGenreId(1);
        when(genreDAO.getGenreByName("Vasua")).thenReturn(expected);
        GenreDTO actual = genreService.getGenre("Vasua");
        assertEquals(expected.getGenreId(), actual.getGenreId());
    }

    @Test
    public void getGenreListTest() {
        List<Genre> expectedGenres = new ArrayList<>();
        Genre expected = createGenre();
        expectedGenres.add(expected);
        when(genreDAO.getGenreList()).thenReturn(java.util.Optional.of(expectedGenres));
        List<GenreDTO> actualGenres = genreService.getAllGenres();
        assertEquals(expectedGenres.get(0).getGenreId(), actualGenres.get(0).getGenreId());
    }

    @Test
    public void createGenreTest() throws InvalidDataException {
        GenreDTO genre = new GenreDTO();
        genre.setGenreName(anyString());
        when(genreValidator.isExistById(genre)).thenReturn(false);
        when(genreValidator.isValid(genre)).thenReturn(true);
        genreService.createGenre(genre);
        verify(genreDAO, atLeastOnce()).createGenre(anyString());
    }

    @Test
    public void removeGenreTest() throws InvalidDataException {
        GenreDTO genre = new GenreDTO();
        genre.setGenreName(anyString());
        when(genreValidator.isExistById(genre)).thenReturn(true);
        genreService.removeGenre(genre);
        verify(genreDAO, atLeastOnce()).removeGenre(anyLong());
    }

    private Genre createGenre() {
        Genre genre = new Genre("Vasua");
        genre.setGenreId(1);
        return genre;
    }
}
