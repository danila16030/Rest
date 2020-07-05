package com.epam.service.impl;

import com.epam.comparator.BookDateComparator;
import com.epam.comparator.BookTitleComparator;
import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.GenreDAO;
import com.epam.dao.impl.fields.BookFields;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.responce.BookResponseDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.entity.Book;
import com.epam.exception.InvalidDataException;
import com.epam.exception.NoSuchElementException;
import com.epam.mapper.Mapper;
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

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

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
        BookResponseDTO bookDTO = mapper.bookToBookDTO(book);
        bookDTO.setGenres(getGenre(bookId));
        return bookDTO;
    }

    @Override
    public BookResponseDTO changeBookPrice(ParametersRequestDTO parameters) {
        long bookId = Long.parseLong(parameters.getParameters().get(BookFields.ID));
        if (bookValidator.isExist(bookId)) {
            float price = Float.parseFloat(parameters.getParameters().get(BookFields.PRICE));
            Book book = bookDAO.changeBookPrice(price, bookId);
            BookResponseDTO bookDTO = mapper.bookToBookDTO(book);
            bookDTO.setGenres(getGenre(bookId));
            return bookDTO;
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<BookResponseDTO> getAllBooks(int limit, int offset) {
        List<Book> bookList = bookDAO.getAllBooks(limit, offset).get();
        List<BookResponseDTO> bookDTOList = mapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookResponseDTO> getBookByPartialCoincidence(ParametersRequestDTO parameters, int limit, int offset) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.searchByPartialCoincidence(parameters, limit, offset).get();
            List<BookResponseDTO> bookDTOList = mapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookResponseDTO> getBookByFullCoincidence(ParametersRequestDTO parameters, int limit, int offset) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.searchByFullCoincidence(parameters, limit, offset).get();
            List<BookResponseDTO> bookDTOList = mapper.bookListToBookDTOList(bookList);
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
        throw new NoSuchElementException();
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
            BookResponseDTO resultBook = mapper.bookToBookDTO(book);
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
            BookResponseDTO resultBook = mapper.bookToBookDTO(book);
            resultBook.setGenres(getGenre(resultBook.getBookId()));
            return resultBook;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookResponseDTO> filter(ParametersRequestDTO parameters, int limit, int offset) {
        if (parametersValidator.isValid(parameters.getParameters())) {
            List<Book> bookList = bookDAO.filter(parameters, limit, offset).get();
            List<BookResponseDTO> bookDTOList = mapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookResponseDTO> getBooksSortedByName(int limit, int offset) {
        List<Book> bookList = bookDAO.getAllBooks(limit, offset).get();
        bookList = bookList.stream().sorted(bookTitleComparator).collect(Collectors.toList());
        List<BookResponseDTO> bookDTOList = mapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    @Override
    public List<BookResponseDTO> getBooksSortedByDate(int limit, int offset) {
        List<Book> bookList = bookDAO.getAllBooks(limit, offset).get();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        List<BookResponseDTO> bookDTOList = mapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    private void setGenreForAllBooks(List<BookResponseDTO> books) {
        for (BookResponseDTO book : books) {
            book.setGenres(getGenre(book.getBookId()));
        }
    }

    private List<GenreResponseDTO> getGenre(long id) {
        return mapper.genreListToGenreDTOList(bookGenreDAO.getAllGenresOnBook(id).get());
    }

}
