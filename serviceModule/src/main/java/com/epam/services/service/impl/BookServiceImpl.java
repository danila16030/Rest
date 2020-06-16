package com.epam.services.service.impl;

import com.epam.services.comparator.BookDateComparator;
import com.epam.services.comparator.BookTitleComparator;
import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.dto.ParametersDTO;
import com.epam.entytys.entyty.Book;
import com.epam.entytys.mapper.BookGenreMapper;
import com.epam.services.service.BookService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {
    private BookDAOImpl bookDAO;
    private BookGenreMapper bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);
    private BookTitleComparator bookTitleComparator = new BookTitleComparator();
    private BookDateComparator bookDateComparator = new BookDateComparator();

    @Autowired
    public BookServiceImpl(BookDAOImpl dao) {
        this.bookDAO = dao;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookDAO.getBookList().get();
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

    @Override
    public List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters) {
        List<Book> book = bookDAO.searchByPartialCoincidence(parameters).get();
        return bookGenreMapper.bookListToBookDTOList(book);
    }

    @Override
    public List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters) {
        List<Book> book = bookDAO.searchByFullCoincidence(parameters).get();
        return bookGenreMapper.bookListToBookDTOList(book);
    }

    @Override
    public boolean removeBook(String bookName) {
        return bookDAO.removeBook(bookName);
    }

    @Override
    public int createBook(BookDTO book) {
        return bookDAO.createNewBook(book.getAuthor(), book.getDescription(), book.getPrice(), book.getWritingDate(),
                book.getNumberOfPages(), book.getTitle());
    }

    @Override
    public boolean updateBook(BookDTO book) {
        return bookDAO.updateBook(book.getTitle(), book.getAuthor(), book.getWritingDate(), book.getDescription(),
                book.getNumberOfPages(), book.getPrice(), book.getOldTitle());
    }

    @Override
    public List<BookDTO> filter(ParametersDTO parameters) {
        List<Book> bookList = bookDAO.filter(parameters).get();
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

    @Override
    public List<BookDTO> getBooksSortedByName() {
        List<Book> bookList = bookDAO.getBookList().get();
        bookList = bookList.stream().sorted(bookTitleComparator).collect(Collectors.toList());
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

    @Override
    public List<BookDTO> getBooksSortedByDate() {
        List<Book> bookList = bookDAO.getBookList().get();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

}
