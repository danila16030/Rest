package com.epam.services.service.impl;

import com.epam.daos.dao.impl.BookGenreDAOImpl;
import com.epam.daos.dao.impl.GenreDAOImpl;
import com.epam.services.exception.InvalidDataException;
import com.epam.services.service.BookService;
import com.epam.services.comparator.BookDateComparator;
import com.epam.services.comparator.BookTitleComparator;
import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.models.dto.BookDTO;
import com.epam.models.dto.ParametersDTO;
import com.epam.models.entity.Book;
import com.epam.models.mapper.BookGenreMapper;
import com.epam.services.validator.BookValidator;
import com.epam.services.validator.ParametersValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {
    private BookDAOImpl bookDAO;
    private BookGenreMapper bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);
    private BookTitleComparator bookTitleComparator;
    private BookDateComparator bookDateComparator;
    private BookValidator bookValidator;
    private GenreDAOImpl genreDAO;
    private BookGenreDAOImpl bookGenreDAO;
    private ParametersValidator parametersValidator;

    @Autowired
    public BookServiceImpl(BookDAOImpl bookDAO, BookDateComparator bookDateComparator,
                           BookTitleComparator bookTitleComparator, BookValidator bookValidator,
                           GenreDAOImpl genreDAO, BookGenreDAOImpl bookGenreDAO, ParametersValidator parametersValidator) {
        this.bookDateComparator = bookDateComparator;
        this.bookTitleComparator = bookTitleComparator;
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.genreDAO = genreDAO;
        this.bookGenreDAO = bookGenreDAO;
        this.parametersValidator = parametersValidator;
    }

    @Override
    public BookDTO getBook(long bookId) {
        Book book = bookDAO.getBookById(bookId);
        return bookGenreMapper.bookToBookDTO(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

    @Override
    public List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> book = bookDAO.searchByPartialCoincidence(parameters).get();
            return bookGenreMapper.bookListToBookDTOList(book);
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> book = bookDAO.searchByFullCoincidence(parameters).get();
            return bookGenreMapper.bookListToBookDTOList(book);
        }
        throw new InvalidDataException();
    }

    @Override
    public boolean removeBook(BookDTO book) {
        if (book != null && bookValidator.isExist(book)) {
            return bookDAO.removeBook(book.getBookId());
        }
        throw new InvalidDataException();
    }

    @Override
    public Book createBook(BookDTO book) {
        if (book != null && bookValidator.isValidForCreate(book)) {
            long bookId = bookDAO.createNewBook(book.getAuthor(), book.getDescription(), book.getPrice(), book.getWritingDate(),
                    book.getNumberOfPages(), book.getTitle());
            String genreName = book.getGenre().getGenreName();
            long genreId = genreDAO.getGenreByName(genreName).getGenreId();
            Book resultBook = new Book(book.getAuthor(), book.getDescription(), book.getPrice(), book.getWritingDate(),
                    book.getNumberOfPages(), book.getTitle());
            if (genreId == 0) {
                genreId = genreDAO.createGenreAndReturnId(genreName);
                bookGenreDAO.createConnection(bookId, genreId);
                return resultBook;
            }
            bookGenreDAO.createConnection(bookId, genreId);
            return resultBook;
        }
        throw new InvalidDataException();
    }

    @Override
    public Book updateBook(BookDTO book) {
        if (book != null && bookValidator.isValidForUpdate(book)) {
            return bookDAO.updateBook(book.getTitle(), book.getAuthor(), book.getWritingDate(), book.getDescription(),
                    book.getNumberOfPages(), book.getPrice(), book.getBookId());
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookDTO> filter(ParametersDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.filter(parameters).get();
            return bookGenreMapper.bookListToBookDTOList(bookList);
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookDTO> getBooksSortedByName() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        bookList = bookList.stream().sorted(bookTitleComparator).collect(Collectors.toList());
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

    @Override
    public List<BookDTO> getBooksSortedByDate() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        return bookGenreMapper.bookListToBookDTOList(bookList);
    }

}
