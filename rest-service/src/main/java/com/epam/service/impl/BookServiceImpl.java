package com.epam.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.GenreDAO;
import com.epam.dao.OrderDAO;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.BookGenre;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import com.epam.exception.NoSuchElementException;
import com.epam.mapper.Mapper;
import com.epam.service.BookService;
import com.epam.validator.BookGenreValidator;
import com.epam.validator.BookValidator;
import com.epam.validator.GenreValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    private BookDAO bookDAO;

    private BookValidator bookValidator;

    private GenreDAO genreDAO;

    private OrderDAO orderDAO;

    private BookGenreDAO bookGenreDAO;

    private BookGenreValidator bookGenreValidator;

    private GenreValidator genreValidator;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO, BookGenreValidator bookGenreValidator, BookValidator bookValidator,
            GenreDAO genreDAO, BookGenreDAO bookGenreDAO, OrderDAO orderDAO, GenreValidator genreValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.genreDAO = genreDAO;
        this.bookGenreDAO = bookGenreDAO;
        this.bookGenreValidator = bookGenreValidator;
        this.genreValidator = genreValidator;
        this.orderDAO = orderDAO;
    }

    /**
     * Returns an Book object by book id
     * The bookId argument specify book in database
     * <p>
     * This method return book by id. If book doesn't exist throw exception NoSuchElementException
     *
     * @param bookId specified book id
     * @return the book at the specified id
     * @throws NoSuchElementException
     * @see Book
     */
    @Override
    public Book getBook(long bookId) {
        Book book = bookDAO.getBookById(bookId);
        book.setGenres(getGenres(bookId));
        return book;
    }

    /**
     * Returns an list of books.
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * <p>
     * This method return list of books from database
     *
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list that contain books from database
     * @see Book
     */
    @Override
    public List<Book> getAllBooks(int limit, int offset) {
        List<Book> bookList = bookDAO.getAllBooks(limit, offset).get();
        setGenreForAllBooks(bookList);
        return bookList;
    }

    /**
     * Returns an list of books those name a partial similar to the argument.
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * The title is the argument by which to search .
     * <p>
     * This method returns list of books those name a partial similar to the argument title.
     * If there were no matches return empty list.
     *
     * @param title  partial name of book
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list that contain books from database
     * @see Book
     */
    @Override
    public List<Book> getBookByPartialCoincidence(String title, int limit, int offset) {
        List<Book> bookList = bookDAO.searchByPartialCoincidence(title, limit, offset).get();
        setGenreForAllBooks(bookList);
        return bookList;
    }

    /**
     * Returns an list of books those name a exact similar to the argument.
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * The title is the argument by which to search .
     * <p>
     * This method returns list of books those name a full similar to the argument title.
     * If there were no matches return empty list.
     *
     * @param title  exact name of book
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list that contain books from database specified by title
     * @see Book
     */
    @Override
    public List<Book> getBookByFullCoincidence(String title, int limit, int offset) {
        List<Book> bookList = bookDAO.searchByFullCoincidence(title, limit, offset).get();
        setGenreForAllBooks(bookList);
        return bookList;

    }

    /**
     * Remove book from database.
     * The bookId argument specify book in database
     * <p>
     * This method remove specified book from database.
     * If book doesn't exist throw exception NoSuchElementException
     *
     * @param bookId specified book id
     * @throws NoSuchElementException
     */
    @Override
    public void removeBook(long bookId) {
        if (bookValidator.isExist(bookId)) {
            if (orderDAO.getOrderByBook(bookId).isEmpty()) {
                bookDAO.removeBook(bookId);
            }
            throw new NoSuchElementException("ordered");
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns the created book
     * The bookDTO argument must contain information about the book being created.
     * <p>
     * This method return book that was created. If genre that link to this book doesn't exist he
     * will be created and connect to the book in database.If argument doesn't valid throw InvalidDataException
     *
     * @param bookDTO object that contain information to be used when creating a book
     * @return the book that was created
     * @throws InvalidDataException
     * @see Book
     * @see CreateBookRequestDTO
     * @see Genre
     */
    @Override
    public Book createBook(CreateBookRequestDTO bookDTO) {
        if (bookDTO != null) {
            Book book = bookDAO.createNewBook(mapper.bookDTOtoBook(bookDTO));
            long bookId = book.getBookId();
            for (CreateGenreRequestDTO genre : bookDTO.getGenres()) {
                String genreName = genre.getGenreName();
                long genreId = genreDAO.getGenreByNameWithoutException(genreName).getGenreId();
                if (genreId == 0) {
                    genreId = genreDAO.createGenreAndReturnId(new Genre(genreName));
                    BookGenre bookGenre = new BookGenre(bookId, genreId);
                    if (!bookGenreValidator.isConnected(bookGenre)) {
                        bookGenreDAO.createConnection(bookGenre);
                    }
                } else {
                    BookGenre bookGenre = new BookGenre(bookId, genreId);
                    if (!bookGenreValidator.isConnected(bookGenre)) {
                        bookGenreDAO.createConnection(bookGenre);
                    }
                }
            }
            book.setGenres(getGenres(bookId));
            return book;
        }
        throw new InvalidDataException();
    }

    /**
     * Returns the updated book
     * The bookDTO argument must contain information about the book being updated.
     * <p>
     * This method return book that was updated. If genre that link to this book doesn't exist he
     * will be created and connect to the book in database.If the genre that was associated with this book no longer
     * belongs to her, their connection will be removed.
     * If book doesn't exist throw exception NoSuchElementException.
     *
     * @param bookDTO object that contain information to be used when updating a book
     * @return the book that was updated
     * @throws NoSuchElementException
     * @see Book
     * @see UpdateBookRequestDTO
     * @see Genre
     */
    @Override
    public Book updateBook(UpdateBookRequestDTO bookDTO) {
        if (bookDTO != null && bookValidator.isExist(bookDTO.getBookId())) {
            Book book = bookDAO.updateBook(mapper.bookDTOtoBook(bookDTO));
            List<CreateGenreRequestDTO> newGenres = bookDTO.getGenres();
            List<Genre> oldGenres = getGenres(book.getBookId());
            connectNew(newGenres, oldGenres, book.getBookId());
            removeOld(newGenres, oldGenres, book.getBookId());
            book.setGenres(getGenres(book.getBookId()));
            return book;
        }
        throw new NoSuchElementException();
    }

    /**
     * Return the most common genre in 3 books
     * The firstId,secondId and thirdId arguments specify books the most common genre of that will be found
     * <p>
     * This method return the most common genre of 3 books that specified by id in arguments.
     * If some genres have equal number the most common genre will be the first one.
     *
     * @param fistId   specifies the first book by id
     * @param secondId specifies the second book by id
     * @param thirdId  specifies the third book by id
     * @return the most common genre
     * @see Genre
     */
    @Override
    public Genre geTheMostCommonGenre(long fistId, long secondId, long thirdId) {
        List<Book> books = new ArrayList<>();
        books.add(bookDAO.getBookById(fistId));
        books.add(bookDAO.getBookById(secondId));
        books.add(bookDAO.getBookById(thirdId));
        Map<Genre, Integer> counter = getGenreCount(books);
        return commonGenre(counter);
    }

    /**
     * Returns a list of books sorted by name.
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * <p>
     * This method return list of books from database sorted by name
     *
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return the list is sorted by name and containing books from the database
     * @see Book
     */
    @Override
    public List<Book> getBooksSortedByAuthor(int limit, int offset) {
        List<Book> bookList = bookDAO.getBookSortedByAuthor(limit, offset).get();
        setGenreForAllBooks(bookList);
        return bookList;
    }

    /**
     * Returns a list of books
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database
     * The title is the argument by which to search
     * The type is the argument specify the action
     * <p>
     * This method calls other methods based on the type
     *
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @param title  exact name of book
     * @param type   the action what will be taken
     * @return the list of books
     * @throws InvalidDataException
     */
    @Override
    public List<Book> getResult(String title, int limit, int offset, String type) {
        if (type != null) {
            if (type.equals("sort")) {
                return getBooksSortedByAuthor(limit, offset);
            }
            if (type.equals("all")) {
                return getAllBooks(limit, offset);
            }
            if (type.equals("full")) {
                return getBookByFullCoincidence(title, limit, offset);
            }
            if (type.equals("partial")) {
                return getBookByPartialCoincidence(title, limit, offset);
            }
        }
        throw new InvalidDataException();
    }

    @Override
    public void saveImage(MultipartFile imageFile) throws Exception {
        String folder = "/image/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }

    /**
     * Returns list of books that now contains genres
     * The books argument contains the books to set genres
     * <p>
     * This method returns a list of books that now contain genres.If this book is not associated with any genre set an
     * empty list
     *
     * @param books list of books that need genres
     * @return list of books that now contain genres
     * @see Book
     */
    private void setGenreForAllBooks(List<Book> books) {
        for (Book book : books) {
            book.setGenres(getGenres(book.getBookId()));
        }
    }

    /**
     * Returns list of genres that connect with book
     * The id argument specifies the book whose genres will be returned
     * <p>
     * This method returns a list of genres that are associated with the book in the database. The book indicated by id.
     * If this book is not associated with any genre returns an empty list
     *
     * @param id specified book id
     * @return list of genres
     * @see Genre
     */
    private List<Genre> getGenres(long id) {
        return bookGenreDAO.getAllGenresOnBook(id).get();
    }

    /**
     * Return map that contains information about the amount of each genre
     * The books argument is list of books.Each book have genres
     * <p>
     * This method counts the number of each genre and returns a map, which key is the genre and the value
     * of how many times the genre was encountered
     *
     * @param books the list of books
     * @return map that contain information about amount of each genre
     * @see Book
     * @see Genre
     */
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

    /**
     * Return the most common genre
     * The count argument is a map that contain information about amount of each genre
     * <p>
     * This method return the most common genre found from map.
     * If some genres have equal number the most common genre will be the first one.
     *
     * @param count map that contain information about amount of each genre
     * @return the most common genre
     * @see Genre
     */
    private Genre commonGenre(Map<Genre, Integer> count) {
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

    /**
     * Links the updated book to new genres.
     * The newGenres argument is a list that contain new genres of updated book. The oldGenres argument is a list that
     * contain information about old genres of updated book.The bookId argument specified updated book.
     * <p>
     * This method connect updated book with new genres.If old genre is equals to the new genre new connection doesnt
     * created.If new genre doesnt exist he created and then connected.
     *
     * @param newGenres the list of new genres
     * @param oldGenres the list of old genres
     * @param bookId    specified book id
     * @see CreateGenreRequestDTO
     * @see Genre
     * @see GenreValidator
     */
    private void connectNew(List<CreateGenreRequestDTO> newGenres, List<Genre> oldGenres, long bookId) {
        for (CreateGenreRequestDTO genre : newGenres) {
            boolean connect = false;
            for (Genre old : oldGenres) {
                if (genre.getGenreName().equals(old.getGenreName())) {
                    connect = true;
                }
            }
            if (!connect) {
                if (!genreValidator.isExistByName(genre.getGenreName())) {
                    long genreId = genreDAO.createGenreAndReturnId(new Genre(genre.getGenreName()));
                    bookGenreDAO.createConnection(new BookGenre(bookId, genreId));
                } else {
                    Genre createdGenre = genreDAO.getGenreByNameWithoutException(genre.getGenreName());
                    bookGenreDAO.createConnection(new BookGenre(bookId, createdGenre.getGenreId()));
                }
            }
        }
    }

    /**
     * Remove connection between updated book and old genre.
     * The newGenres argument is a list that contain new genres of updated book. The oldGenres argument is a list that
     * contain information about old genres of updated book.The bookId argument specified updated book.
     * <p>
     * This method is removed connection between old genre and updated book if new genre doesnt equals to the new genre.
     *
     * @param newGenres the list of new genres
     * @param oldGenres the list of old genres
     * @param bookId    specified book id
     * @see CreateGenreRequestDTO
     * @see Genre
     */
    private void removeOld(List<CreateGenreRequestDTO> newGenres, List<Genre> oldGenres, long bookId) {
        for (Genre genre : oldGenres) {
            boolean connected = false;
            for (CreateGenreRequestDTO newGenre : newGenres) {
                if (genre.getGenreName().equals(newGenre.getGenreName())) {
                    connected = true;
                }
            }
            if (!connected) {
                bookGenreDAO.removeConnection(new BookGenre(bookId, genre.getGenreId()));
            }
        }
    }
}
