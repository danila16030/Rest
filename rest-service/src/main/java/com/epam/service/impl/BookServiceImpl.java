package com.epam.service.impl;

import com.epam.comparator.BookDateComparator;
import com.epam.comparator.BookTitleComparator;
import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.GenreDAO;
import com.epam.dto.request.CreateBookRequestDTO;
import com.epam.dto.request.CreateGenreRequestDTO;
import com.epam.dto.request.UpdateBookRequestDTO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.responce.BookResponseDTO;
import com.epam.dto.responce.GenreResponseDTO;
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
    private BookTitleComparator bookTitleComparator;
    private BookDateComparator bookDateComparator;
    private BookValidator bookValidator;
    private GenreDAO genreDAO;
    private BookGenreDAO bookGenreDAO;
    private ParametersValidator parametersValidator;

    private final BookGenreMapper genreMapper = Mappers.getMapper(BookGenreMapper.class);

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
    public BookResponseDTO getBook(long bookId) {
        Book book = bookDAO.getBookById(bookId);
        BookResponseDTO bookDTO = genreMapper.bookToBookDTO(book);
        bookDTO.setGenres(getGenre(bookId));
        return bookDTO;
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        List<BookResponseDTO> bookDTOList = genreMapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookResponseDTO> getBookByPartialCoincidence(ParametersRequestDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.searchByPartialCoincidence(parameters).get();
            List<BookResponseDTO> bookDTOList = genreMapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookResponseDTO> getBookByFullCoincidence(ParametersRequestDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.searchByFullCoincidence(parameters).get();
            List<BookResponseDTO> bookDTOList = genreMapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public boolean removeBook(long bookId) {
        if (bookValidator.isExist(bookId)) {
            return bookDAO.removeBook(bookId);
        }
        throw new InvalidDataException();
    }

    @Override
    public BookResponseDTO createBook(CreateBookRequestDTO bookDTO) {
        if (bookDTO != null && bookValidator.isValidForCreate(bookDTO)) {
            long bookId = bookDAO.createNewBook(bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getPrice(),
                    bookDTO.getWritingDate(), bookDTO.getNumberOfPages(), bookDTO.getTitle());
            for (CreateGenreRequestDTO genre : bookDTO.getGenres()) {
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
            BookResponseDTO resultBook = genreMapper.bookToBookDTO(book);
            resultBook.setGenres(getGenre(bookId));
            return resultBook;
        }
        throw new InvalidDataException();
    }

    @Override
    public BookResponseDTO updateBook(UpdateBookRequestDTO bookDTO) {
        if (bookDTO != null && bookValidator.isValidForUpdate(bookDTO)) {
            Book book = bookDAO.updateBook(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getWritingDate(),
                    bookDTO.getDescription(), bookDTO.getNumberOfPages(), bookDTO.getPrice(), bookDTO.getBookId());
            BookResponseDTO resultBook = genreMapper.bookToBookDTO(book);
            resultBook.setGenres(getGenre(resultBook.getBookId()));
            return resultBook;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookResponseDTO> filter(ParametersRequestDTO parameters) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.filter(parameters).get();
            List<BookResponseDTO> bookDTOList = genreMapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookResponseDTO> getBooksSortedByName() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        bookList = bookList.stream().sorted(bookTitleComparator).collect(Collectors.toList());
        List<BookResponseDTO> bookDTOList = genreMapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookResponseDTO> getBooksSortedByDate() {
        List<Book> bookList = bookDAO.getAllBooks().get();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        List<BookResponseDTO> bookDTOList = genreMapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    private void setGenreForAllBooks(List<BookResponseDTO> books) {
        for (BookResponseDTO book : books) {
            book.setGenres(getGenre(book.getBookId()));
        }
    }

    private List<GenreResponseDTO> getGenre(long id) {
        return genreMapper.genreListToGenreDTOList(bookGenreDAO.getAllGenresOnBook(id).get());
    }

}
