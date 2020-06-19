package com.epam.serviceTest;

import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.daos.dao.impl.fields.BookFields;
import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.dto.ParametersDTO;
import com.epam.entytys.entyty.Book;
import com.epam.services.comparator.BookDateComparator;
import com.epam.services.comparator.BookTitleComparator;
import com.epam.services.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class BookService {
    @Mock
    private BookDAOImpl bookDAO;

    @InjectMocks
    BookServiceImpl bookService;

    @InjectMocks
    private BookTitleComparator bookTitleComparator = new BookTitleComparator();

    @InjectMocks
    private BookDateComparator bookDateComparator = new BookDateComparator();


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    private Book createBook() {
        Book book = new Book();
        book.setAuthor("Vasua");
        book.setDescription("страшно");
        book.setNumberOfPages(88);
        book.setWritingDate("16.03.2200");
        book.setPrice(985);
        book.setTitle("It");
        return book;
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
        bookService.removeBook(anyString());
        verify(bookDAO, atLeastOnce()).removeBook(anyString());
    }

    @Test
    public void createBookTest() {
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
