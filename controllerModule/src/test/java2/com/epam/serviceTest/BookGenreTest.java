package com.epam.serviceTest;

import com.epam.daos.dao.BookGenreDAO;
import com.epam.daos.dao.impl.BookGenreDAOImpl;
import com.epam.daos.dao.impl.GenreDAOImpl;
import com.epam.services.service.impl.BookGenreServiceImpl;
import com.epam.services.service.impl.GenreServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class BookGenreTest {
    @Mock
    private BookGenreDAOImpl bookGenreDAO;

    @InjectMocks
    BookGenreServiceImpl bookGenreService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createConnectionTest() {
        bookGenreService.createConnection(anyInt(), anyInt());
        verify(bookGenreDAO, atLeastOnce()).createConnection(anyInt(), anyInt());
    }


}
