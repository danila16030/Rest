package com.epam.service.impl;

import com.epam.comparator.BookDateComparator;
import com.epam.comparator.BookTitleComparator;
import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.GenreDAO;
import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.dto.ParametersDTO;
import com.epam.entity.Book;
import com.epam.exception.InvalidDataException;
import com.epam.mapper.BookGenreMapper;
import com.epam.service.BookService;
import com.epam.validator.BookValidator;
import com.epam.validator.ParametersValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {
    private BookDAO bookDAO;
    private BookGenreMapper bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);
    private BookTitleComparator bookTitleComparator;
    private BookDateComparator bookDateComparator;
    private BookValidator bookValidator;
    private GenreDAO genreDAO;
    private BookGenreDAO bookGenreDAO;
    private ParametersValidator parametersValidator;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO, BookDateComparator bookDateComparator,
                           BookTitleComparator bookTitleComparator, BookValidator bookValidator,
                           GenreDAO genreDAO, BookGenreDAO bookGenreDAO, ParametersValidator parametersValidator) {
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
        BookDTO bookDTO = bookGenreMapper.bookToBookDTO(book);
        bookDTO.setGenres(getGenre(bookId));
        return bookDTO;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        List<BookDTO> bookDTOList = bookGenreMapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookDTO> getBookByPartialCoincidence(ParametersDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.searchByPartialCoincidence(parameters).get();
            List<BookDTO> bookDTOList = bookGenreMapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookDTO> getBookByFullCoincidence(ParametersDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.searchByFullCoincidence(parameters).get();
            List<BookDTO> bookDTOList = bookGenreMapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public boolean removeBook(BookDTO book) {
        if (book != null && bookValidator.isExist(book.getBookId())) {
            return bookDAO.removeBook(book.getBookId());
        }
        throw new InvalidDataException();
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        if (bookDTO != null && bookValidator.isValidForCreate(bookDTO)) {
            long bookId = bookDAO.createNewBook(bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getPrice(),
                    bookDTO.getWritingDate(), bookDTO.getNumberOfPages(), bookDTO.getTitle());
            for (GenreDTO genre : bookDTO.getGenres()) {
                String genreName = genre.getGenreName();
                long genreId = genreDAO.getGenreByNameWithoutException(genreName).getGenreId();
                if (genreId == 0) {
                    genreId = genreDAO.createGenreAndReturnId(genreName);
                    bookGenreDAO.createConnection(bookId, genreId);
                } else {
                    bookGenreDAO.createConnection(bookId, genreId);
                }
            }
            Book book = new Book(bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getPrice(), bookDTO.getWritingDate(),
                    bookDTO.getNumberOfPages(), bookDTO.getTitle(), bookId);
            BookDTO resultBook = bookGenreMapper.bookToBookDTO(book);
            resultBook.setGenres(getGenre(bookId));
            return resultBook;
        }
        throw new InvalidDataException();
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        if (bookDTO != null && bookValidator.isValidForUpdate(bookDTO)) {
            Book book = bookDAO.updateBook(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getWritingDate(),
                    bookDTO.getDescription(), bookDTO.getNumberOfPages(), bookDTO.getPrice(), bookDTO.getBookId());
            BookDTO resultBook = bookGenreMapper.bookToBookDTO(book);
            resultBook.setGenres(getGenre(resultBook.getBookId()));
            return resultBook;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookDTO> filter(ParametersDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.filter(parameters).get();
            List<BookDTO> bookDTOList = bookGenreMapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookDTO> getBooksSortedByName() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        bookList = bookList.stream().sorted(bookTitleComparator).collect(Collectors.toList());
        List<BookDTO> bookDTOList = bookGenreMapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookDTO> getBooksSortedByDate() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        List<BookDTO> bookDTOList = bookGenreMapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    private void setGenreForAllBooks(List<BookDTO> books) {
        for (BookDTO book : books) {
            book.setGenres(getGenre(book.getBookId()));
        }
    }

    private List<GenreDTO> getGenre(long id) {
        return bookGenreMapper.genreListToGenreDTOList(bookGenreDAO.getAllGenresOnBook(id).get());
    }

}
