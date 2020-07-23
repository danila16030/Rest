package com.epam.service.impl;

import com.epam.dao.BookGenreDAO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import com.epam.service.BookGenreService;
import com.epam.validator.BookValidator;
import com.epam.validator.GenreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookGenreServiceImpl implements BookGenreService {

    private BookGenreDAO bookGenreDAO;
    private GenreValidator genreValidator;
    private BookValidator bookValidator;

    @Autowired
    public BookGenreServiceImpl(BookGenreDAO bookGenreDAO, GenreValidator genreValidator,
                                BookValidator bookValidator) {
        this.bookGenreDAO = bookGenreDAO;
        this.genreValidator = genreValidator;
        this.bookValidator = bookValidator;
    }

    /**
     * Return genres of book
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database.
     * The bookId argument specify book.
     * <p>
     * This method return List of genres that connected with book that specify by id. If book doesnt have any genres it
     * will return empty list.
     *
     * @param bookId specified book id
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list of genres
     * @see Genre
     */
    @Override
    public List<Genre> getGenresByBook(long bookId, int limit, int offset) {
        if (bookValidator.isExist(bookId)) {
            return bookGenreDAO.getAllGenresOnBook(bookId, limit, offset).get();
        }
        throw new InvalidDataException();
    }
    /**
     * Return list of books that connected to the genre
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database.
     * The genreId argument specify genre.
     * <p>
     * This method return List of books that connected with genre that specify by id. If genre doesnt have any books it
     * will return empty list.
     *
     * @param genreId specified genre id
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return list of books
     * @see Book
     */
    @Override
    public List<Book> getBooksByGenre(long genreId, int limit, int offset) {
        if (genreValidator.isExistById(genreId)) {
            List<Book> bookList = bookGenreDAO.getAllBooksByGenre(genreId, limit, offset).get();
            setGenreForAllBooks(bookList);
            return bookList;
        }
        throw new InvalidDataException();
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
            book.setGenres(getGenre(book.getBookId()));
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
    private List<Genre> getGenre(long id) {
        return bookGenreDAO.getAllGenresOnBook(id).get();
    }

}
