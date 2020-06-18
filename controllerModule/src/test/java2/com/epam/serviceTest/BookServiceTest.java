package com.epam.serviceTest;

import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.entyty.Book;
import com.epam.services.service.impl.BookGenreServiceImpl;
import com.epam.services.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class BookServiceTest {
    @Mock
    private BookDAOImpl bookDAO;

    @InjectMocks
    BookServiceImpl bookService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBookByNameTest() {
        bookService.getBookByName(anyString());
        verify(bookDAO, atLeastOnce()).getBookByName(anyString());
    }

    @Test
    public void removeBookTest() {
        bookService.removeBook(anyString());
        verify(bookDAO, atLeastOnce()).removeBook(anyString());
    }

    @Test
    public void createBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor(anyString());
        bookDTO.setDescription(anyString());
        bookDTO.setNumberOfPages(anyInt());
        bookDTO.setWritingDate(anyString());
        bookDTO.setPrice(anyFloat());
        bookDTO.setTitle(anyString());
        bookService.createBook(bookDTO);
        verify(bookDAO, atLeastOnce()).createNewBook(anyString(), anyString(), anyInt(), anyString(), anyInt(), anyString());
    }

    @Test
    public void updateBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor(anyString());
        bookDTO.setDescription(anyString());
        bookDTO.setNumberOfPages(anyInt());
        bookDTO.setWritingDate(anyString());
        bookDTO.setPrice(anyFloat());
        bookDTO.setTitle(anyString());
        bookDTO.setOldTitle(anyString());
        bookService.updateBook(bookDTO);
        verify(bookDAO, atLeastOnce()).updateBook(anyString(), anyString(), anyString(), anyString(), anyInt(), anyFloat(), anyString());
    }
}
