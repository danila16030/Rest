package com.epam.serviceTest;

import com.epam.config.TestConfig;
import com.epam.controll.config.Config;
import com.epam.daos.dao.GenreDAO;
import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.daos.dao.impl.GenreDAOImpl;
import com.epam.entytys.dto.GenreDTO;
import com.epam.entytys.entyty.Book;
import com.epam.entytys.entyty.Genre;
import com.epam.entytys.mapper.BookGenreMapper;
import com.epam.services.service.impl.BookServiceImpl;
import com.epam.services.service.impl.GenreServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GenreServiceImplTest {
    @Mock
    private GenreDAOImpl genreDAO;

    @InjectMocks
    GenreServiceImpl genreService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getGenreByNameTest() {
        genreService.getGenre(anyString());
        verify(genreDAO, atLeastOnce()).getGenreByName(anyString());
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
