package com.epam.rest.service;


import com.epam.rest.config.TestConfig;
import com.epam.services.comparator.BookDateComparator;
import com.epam.services.comparator.BookTitleComparator;
import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.daos.dao.impl.fields.BookFields;
import com.epam.models.dto.BookDTO;
import com.epam.models.dto.ParametersDTO;
import com.epam.models.entity.Book;
import com.epam.services.service.BookService;
import com.epam.services.validator.BookValidator;
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
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BookServiceTest {
    @Mock
    private BookDAOImpl bookDAO;

    @Autowired
    @InjectMocks
    private BookService bookService;

    @Autowired
    @Mock
    private BookTitleComparator bookTitleComparator;

    @Autowired
    @Mock
    private BookValidator bookValidator;

    @Autowired
    @Mock
    private BookDateComparator bookDateComparator;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getBookByNameTest() {
        Book expectedBook = createBook();
        when(bookDAO.getBookByName("It")).thenReturn(expectedBook);
        BookDTO actualBook = bookService.getBookByName("It");
        assertEquals(expectedBook.getWritingDate(), actualBook.getWritingDate());
    }

    @Test
    public void getAllBooksTest() {
        List<Book> bookList = new ArrayList<>();
        Book expectedBook = createBook();
        bookList.add(expectedBook);
        when(bookDAO.getAllBooks()).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getAllBooks();
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }

    @Test
    public void getBookByPartialCoincidenceTest() {
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "a");
        List<Book> bookList = new ArrayList<>();
        Book expectedBook = createBook();
        bookList.add(expectedBook);
        when(bookDAO.searchByPartialCoincidence(parametersDTO)).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getBookByPartialCoincidence(parametersDTO);
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }

    @Test
    public void getBookByFullCoincidenceTest() {
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "Vasua");
        List<Book> bookList = new ArrayList<>();
        Book expectedBook = createBook();
        bookList.add(expectedBook);
        when(bookDAO.searchByFullCoincidence(parametersDTO)).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getBookByFullCoincidence(parametersDTO);
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }

    @Test
    public void filterTest() {
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "Vasua");
        parametersDTO.getParameters().put(BookFields.PRICE, "985");
        List<Book> bookList = new ArrayList<>();
        Book expectedBook = createBook();
        bookList.add(expectedBook);
        when(bookDAO.filter(parametersDTO)).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.filter(parametersDTO);
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }


    @Test
    public void getBooksSortedByNameTest() {
        List<Book> bookList = new ArrayList<>();
        Book book = createBook();
        bookList.add(book);
        Book book1 = createBook();
        book1.setTitle("Ana");
        bookList.add(book1);
        when(bookDAO.getAllBooks()).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getBooksSortedByName();
        bookList = bookList.stream().sorted(bookTitleComparator).collect(Collectors.toList());
        assertEquals(bookList.get(0).getTitle(), actualBooks.get(0).getTitle());
    }

    @Test
    public void getBooksSortedByDateTest() {
        List<Book> bookList = new ArrayList<>();
        Book book = createBook();
        bookList.add(book);
        Book book1 = createBook();
        book1.setWritingDate("16.03.1999");
        bookList.add(book1);
        when(bookDAO.getAllBooks()).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getBooksSortedByDate();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }


    @Test
    public void removeBookTest() {
        BookDTO book = new BookDTO();
        when(bookValidator.isExist(book)).thenReturn(true);
        bookService.removeBook(book);
        verify(bookDAO, atLeastOnce()).removeBook(anyLong());
    }


    @Test
    public void updateBook() {
        BookDTO book = new BookDTO();
        when(bookValidator.isExist(book)).thenReturn(true);
        bookService.updateBook(book);
        verify(bookDAO, atLeastOnce()).updateBook(anyString(),anyString(),anyString(),anyString(),anyInt(),anyInt(),anyLong());
    }

    private Book createBook() {
        Book book = new Book();
        book.setAuthor("Vasua");
        book.setDescription("creepy");
        book.setNumberOfPages(88);
        book.setWritingDate("16.03.2200");
        book.setPrice(985);
        book.setTitle("It");
        return book;
    }
}
