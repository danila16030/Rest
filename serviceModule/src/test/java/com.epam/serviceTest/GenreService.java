package com.epam.serviceTest;

import com.epam.daos.dao.impl.GenreDAOImpl;
import com.epam.entytys.dto.GenreDTO;
import com.epam.entytys.entyty.Genre;
import com.epam.services.service.impl.GenreServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GenreService {
    @Mock
    private GenreDAOImpl genreDAO;

    @InjectMocks
    GenreServiceImpl genreService;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    private Genre createGenre(){
        Genre genre = new Genre();
        genre.setGenreName("Vasua");
        genre.setGenreId(1);
        return genre;
    }

    @Test
    public void getGenreByNameTest() {
        Genre expected =createGenre();
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
    public void createGenreTest() {
        genreService.createGenre(anyString());
        verify(genreDAO, atLeastOnce()).createGenre(anyString());
    }

    @Test
    public void removeGenreTest() {
        genreService.removeGenre(anyString());
        verify(genreDAO, atLeastOnce()).removeGenre(anyString());
    }
}
