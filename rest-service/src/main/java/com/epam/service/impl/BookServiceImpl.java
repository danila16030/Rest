package com.epam.service.impl;

import com.epam.comparator.BookDateComparator;
import com.epam.comparator.BookTitleComparator;
import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.GenreDAO;
import com.epam.dao.impl.fields.BookFields;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import com.epam.exception.NoSuchElementException;
import com.epam.service.BookService;
import com.epam.validator.BookGenreValidator;
import com.epam.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {
    private BookDAO bookDAO;
    private BookTitleComparator bookTitleComparator;
    private BookDateComparator bookDateComparator;
    private BookValidator bookValidator;
    private GenreDAO genreDAO;
    private BookGenreDAO bookGenreDAO;
    private BookGenreValidator bookGenreValidator;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO, BookDateComparator bookDateComparator, BookGenreValidator bookGenreValidator,
                           BookTitleComparator bookTitleComparator, BookValidator bookValidator,
                           GenreDAO genreDAO, BookGenreDAO bookGenreDAO) {
        this.bookDateComparator = bookDateComparator;
        this.bookTitleComparator = bookTitleComparator;
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.genreDAO = genreDAO;
        this.bookGenreDAO = bookGenreDAO;
        this.bookGenreValidator = bookGenreValidator;
    }

    @Override
    public Book getBook(long bookId) {
        Book book = bookDAO.getBookById(bookId);
        book.setGenres(getGenre(bookId));
        return book;
    }

    @Override
    public Book changeBookPrice(ParametersRequestDTO parameters) {
        long bookId = Long.parseLong(parameters.getParameters().get(BookFields.ID));
        if (bookValidator.isExist(bookId)) {
            float price = Float.parseFloat(parameters.getParameters().get(BookFields.PRICE));
            Book book = bookDAO.changeBookPrice(price, bookId);
            book.setGenres(getGenre(bookId));
            return book;
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<Book> getAllBooks(int limit, int offset) {
        List<Book> bookList = bookDAO.getAllBooks(limit, offset).get();
        setGenreForAllBooks(bookList);
        return bookList;
    }

    @Override
    public List<Book> getBookByPartialCoincidence(String title, int limit, int offset) {
        List<Book> bookList = bookDAO.searchByPartialCoincidence(title, limit, offset).get();
        setGenreForAllBooks(bookList);
        return bookList;
    }

    @Override
    public List<Book> getBookByFullCoincidence(String title, int limit, int offset) {
        List<Book> bookList = bookDAO.searchByFullCoincidence(title, limit, offset).get();
        setGenreForAllBooks(bookList);
        return bookList;

    }

    @Override
    public void removeBook(long bookId) {
        if (bookValidator.isExist(bookId)) {
            bookDAO.removeBook(bookId);
        }
        throw new NoSuchElementException();
    }

    @Override
    public Book createBook(CreateBookRequestDTO bookDTO) {
        if (bookDTO != null && bookValidator.isValidForCreate(bookDTO)) {
            long bookId = bookDAO.createNewBook(bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getPrice(),
                    bookDTO.getWritingDate(), bookDTO.getNumberOfPages(), bookDTO.getTitle());
            for (CreateGenreRequestDTO genre : bookDTO.getGenres()) {
                String genreName = genre.getGenreName();
                long genreId = genreDAO.getGenreByNameWithoutException(genreName).getGenreId();
                if (genreId == 0) {
                    genreId = genreDAO.createGenreAndReturnId(genreName);
                    if (!bookGenreValidator.isConnected(bookId, genreId)) {
                        bookGenreDAO.createConnection(bookId, genreId);
                    }
                } else {
                    if (!bookGenreValidator.isConnected(bookId, genreId)) {
                        bookGenreDAO.createConnection(bookId, genreId);
                    }
                }
            }
            Book book = new Book(bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getPrice(), bookDTO.getWritingDate(),
                    bookDTO.getNumberOfPages(), bookDTO.getTitle(), bookId);
            book.setGenres(getGenre(bookId));
            return book;
        }
        throw new InvalidDataException();
    }

    @Override
    public Book updateBook(UpdateBookRequestDTO bookDTO) {
        if (bookDTO != null && bookValidator.isValidForUpdate(bookDTO)) {
            Book book = bookDAO.updateBook(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getWritingDate(),
                    bookDTO.getDescription(), bookDTO.getNumberOfPages(), bookDTO.getPrice(), bookDTO.getBookId());
            book.setGenres(getGenre(book.getBookId()));
            return book;
        }
        throw new InvalidDataException();
    }

    @Override
    public Genre getTopGenre(long fist, long second, long third) {
        List<Book> books = new ArrayList<>();
        books.add(bookDAO.getBookById(fist));
        books.add(bookDAO.getBookById(second));
        books.add(bookDAO.getBookById(third));
        Map<Genre, Integer> counter = getGenreCount(books);
        return topGenre(counter);
    }


    @Override
    public List<Book> getBooksSortedByName(int limit, int offset) {
        List<Book> bookList = bookDAO.getAllBooks(limit, offset).get();
        bookList = bookList.stream().sorted(bookTitleComparator).collect(Collectors.toList());
        setGenreForAllBooks(bookList);
        return bookList;
    }

    @Override
    public List<Book> getBooksSortedByDate(int limit, int offset) {
        List<Book> bookList = bookDAO.getAllBooks(limit, offset).get();
        bookList = bookList.stream().sorted(bookDateComparator).collect(Collectors.toList());
        setGenreForAllBooks(bookList);
        return bookList;
    }

    private void setGenreForAllBooks(List<Book> books) {
        for (Book book : books) {
            book.setGenres(getGenre(book.getBookId()));
        }
    }

    private List<Genre> getGenre(long id) {
        return bookGenreDAO.getAllGenresOnBook(id).get();
    }

    private Map<Genre, Integer> getGenreCount(List<Book> books) {
        Map<Genre, Integer> counter = new HashMap<>();
        for (Book book : books) {
            book.setGenres(bookGenreDAO.getAllGenresOnBook(book.getBookId()).get());
            for (Genre genre : book.getGenres()) {
                if (counter.get(genre) != null) {
                    counter.put(genre, counter.get(genre) + 1);
                } else {
                    counter.put(genre, 1);
                }
            }
        }
        return counter;
    }

    private Genre topGenre(Map<Genre, Integer> count) {
        AtomicInteger maxCount = new AtomicInteger();
        final Genre[] topGenre = new Genre[1];
        count.forEach((k, v) -> {
            if (maxCount.get() < v) {
                topGenre[0] = k;
                maxCount.set(v);
            }
        });
        return topGenre[0];
    }
}
