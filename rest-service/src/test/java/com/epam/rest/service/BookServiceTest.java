package com.epam.rest.service;


import com.epam.comparator.BookDateComparator;
import com.epam.comparator.BookTitleComparator;
import com.epam.dao.impl.BookDAOImpl;
import com.epam.dao.impl.BookGenreDAOImpl;
import com.epam.dao.impl.fields.BookFields;
import com.epam.dto.BookDTO;
import com.epam.dto.ParametersDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.rest.config.TestConfig;
import com.epam.service.BookService;
import com.epam.validator.BookValidator;
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

    @Mock
    private BookGenreDAOImpl bookGenreDAO;

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
        when(bookDAO.getBookById(1)).thenReturn(expectedBook);
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
        BookDTO actualBook = bookService.getBook(1);
        assertEquals(expectedBook.getWritingDate(), actualBook.getWritingDate());
    }

    @Test
    public void getAllBooksTest() {
        List<Book> bookList = new ArrayList<>();
        Book expectedBook = createBook();
        bookList.add(expectedBook);
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
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
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
        when(bookDAO.searchByPartialCoincidence(parametersDTO)).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getBookByPartialCoincidence(parametersDTO);
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }

    @Test
    public void getBookByFullCoincidenceTest(){
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "Vasua");
        List<Book> bookList = new ArrayList<>();
        Book expectedBook = createBook();
        bookList.add(expectedBook);
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
        when(bookDAO.searchByFullCoincidence(parametersDTO)).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getBookByFullCoincidence(parametersDTO);
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }

    @Test
    public void filterTest()  {
        ParametersDTO parametersDTO = new ParametersDTO();
        parametersDTO.getParameters().put(BookFields.AUTHOR, "Vasua");
        parametersDTO.getParameters().put(BookFields.PRICE, "985");
        List<Book> bookList = new ArrayList<>();
        Book expectedBook = createBook();
        bookList.add(expectedBook);
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
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
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
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
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
        when(bookDAO.getAllBooks()).thenReturn(java.util.Optional.of(bookList));
        List<BookDTO> actualBooks = bookService.getBooksSortedByDate();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        assertEquals(bookList.get(0).getWritingDate(), actualBooks.get(0).getWritingDate());
    }


    @Test
    public void removeBookTest()  {
        BookDTO book = new BookDTO();
        when(bookValidator.isExist(anyLong())).thenReturn(true);
        bookService.removeBook(book);
        verify(bookDAO, atLeastOnce()).removeBook(anyLong());
    }


    @Test
    public void updateBook()  {
        BookDTO bookDTO = new BookDTO();
        Book book = createBook();
        List<Genre> genres = new ArrayList<>();
        genres.add(createGenre());
        when(bookValidator.isValidForUpdate(bookDTO)).thenReturn(true);
        when(bookDAO.updateBook(anyString(), anyString(), anyString(), anyString(), anyInt(), anyFloat(), anyLong())).thenReturn(book);
        when(bookGenreDAO.getAllGenresOnBook(anyInt())).thenReturn(java.util.Optional.of(genres));
        BookDTO result=bookService.updateBook(bookDTO);
        assertEquals(book.getTitle(), result.getTitle());
    }

    private Book createBook() {
        return new Book("Vasua", "creepy", 985, "16.03.2200", 88,
                "It");
    }

    private Genre createGenre() {
        return new Genre("horor");
    }
}
