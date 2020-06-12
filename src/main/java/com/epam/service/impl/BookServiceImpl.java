package com.epam.service.impl;

import com.epam.dao.impl.BookDAOImpl;
import com.epam.dto.BookDTO;
import com.epam.entyty.Book;
import com.epam.mapper.BookGenreMapper;
import com.epam.service.BookService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    private BookDAOImpl bookDAO;
    private BookGenreMapper bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);

    @Autowired
    public BookServiceImpl(BookDAOImpl dao) {
        this.bookDAO = dao;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookDAO.getBookList();
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

    @Override
    public BookDTO getBook(String bookName) {
        Book book = bookDAO.getBookByName(bookName);
        return bookGenreMapper.bookToBookDTO(book);
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

}
