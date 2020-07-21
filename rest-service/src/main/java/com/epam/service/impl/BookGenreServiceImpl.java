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


    @Override
    public List<Genre> getGenresByBook(long bookId, int limit, int offset) {
        if (bookValidator.isExist(bookId)) {
            return bookGenreDAO.getAllGenresOnBook(bookId, limit, offset).get();
        }
        throw new InvalidDataException();
    }

    @Override
    public List<Book> getBooksByGenre(long genreId, int limit, int offset) {
        if (genreValidator.isExistById(genreId)) {
            List<Book> bookList = bookGenreDAO.getAllBooksByGenre(genreId, limit, offset).get();
            setGenreForAllBooks(bookList);
            return bookList;
        }
        throw new InvalidDataException();
    }


    private void setGenreForAllBooks(List<Book> books) {
        for (Book book : books) {
            book.setGenres(getGenre(book.getBookId()));
        }
    }

    private List<Genre> getGenre(long id) {
        return bookGenreDAO.getAllGenresOnBook(id).get();
    }

}
