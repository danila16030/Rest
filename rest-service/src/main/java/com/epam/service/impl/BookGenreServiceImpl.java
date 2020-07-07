package com.epam.service.impl;

import com.epam.dao.BookGenreDAO;
import com.epam.dto.request.ParametersRequestDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import com.epam.mapper.Mapper;
import com.epam.service.BookGenreService;
import com.epam.validator.BookGenreValidator;
import com.epam.validator.BookValidator;
import com.epam.validator.GenreValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookGenreServiceImpl implements BookGenreService {

    private BookGenreDAO bookGenreDAO;
    private GenreValidator genreValidator;
    private BookValidator bookValidator;
    private BookGenreValidator bookGenreValidator;
    private final Mapper genreMapper = Mappers.getMapper(Mapper.class);

    @Autowired
    public BookGenreServiceImpl(BookGenreDAO bookGenreDAO, GenreValidator genreValidator,
                                BookValidator bookValidator, BookGenreValidator bookGenreValidator) {
        this.bookGenreDAO = bookGenreDAO;
        this.genreValidator = genreValidator;
        this.bookValidator = bookValidator;
        this.bookGenreValidator = bookGenreValidator;
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

    @Override
    public void createConnection(long bookId, long genreId) {
        if (bookGenreValidator.isConnected(bookId, genreId)) {
            bookGenreDAO.createConnection(bookId, genreId);
        }
    }

    @Override
    public Book getBookBySeveralGenres(ParametersRequestDTO parametersRequestDTO) {
        Book book = bookGenreDAO.getBookByGenres(parametersRequestDTO);
        return null;
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
