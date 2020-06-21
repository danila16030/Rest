package com.epam.rest.service;

import com.epam.rest.config.TestConfig;
import com.epam.daos.dao.impl.BookGenreDAOImpl;
import com.epam.models.dto.BookDTO;
import com.epam.models.dto.GenreDTO;
import com.epam.models.entity.Book;
import com.epam.models.entity.Genre;
import com.epam.services.service.BookGenreService;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BookGenreServiceTest {
    @Mock
    private BookGenreDAOImpl bookGenreDAO;

    @Autowired
    @InjectMocks
    BookGenreService bookGenreService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createConnectionTest() {
        bookGenreService.createConnection(anyInt(), anyInt());
        verify(bookGenreDAO, atLeastOnce()).createConnection(anyInt(), anyInt());
    }

    @Test
    public void getGenresByBook() {
        List<Genre> expectedGenreList=new ArrayList<>();
        Genre genre=new Genre();
        genre.setGenreName("horor");
        expectedGenreList.add(genre);
        when(bookGenreDAO.getAllGenresOnBook(1)).thenReturn(java.util.Optional.of(expectedGenreList));
        List<GenreDTO> actualGenreList=bookGenreService.getGenresByBook(1);
        assertEquals(expectedGenreList.get(0).getGenreName(), actualGenreList.get(0).getGenreName());
    }

    @Test
    public void getBooksByGenre() {
        List<Book> expectedBookList=new ArrayList<>();
        Book book = new Book();
        book.setAuthor("Vasua");
        book.setDescription("страшно");
        book.setNumberOfPages(88);
        book.setWritingDate("16.03.2200");
        book.setPrice(985);
        book.setTitle("It");
        expectedBookList.add(book);
        when(bookGenreDAO.getAllBooksByGenre(1)).thenReturn(java.util.Optional.of(expectedBookList));
        List<BookDTO> actualBookList=bookGenreService.getBooksByGenre(1);
        assertEquals(expectedBookList.get(0).getTitle(), actualBookList.get(0).getTitle());
    }


}
