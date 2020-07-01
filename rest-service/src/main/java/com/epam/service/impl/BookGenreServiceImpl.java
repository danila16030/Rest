package com.epam.service.impl;

import com.epam.dao.BookGenreDAO;
import com.epam.dto.responce.BookResponseDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.exception.InvalidDataException;
import com.epam.mapper.BookGenreMapper;
import com.epam.service.BookGenreService;
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
    private final BookGenreMapper genreMapper = Mappers.getMapper(BookGenreMapper.class);
    @Autowired
    public BookGenreServiceImpl(BookGenreDAO bookGenreDAO, GenreValidator genreValidator,
                                BookValidator bookValidator) {
        this.bookGenreDAO = bookGenreDAO;
        this.genreValidator = genreValidator;
        this.bookValidator = bookValidator;
    }


    @Override
    public List<GenreResponseDTO> getGenresByBook(long bookId) {
        if (bookValidator.isExist(bookId)) {
            List<Genre> genres = bookGenreDAO.getAllGenresOnBook(bookId).get();
            return genreMapper.genreListToGenreDTOList(genres);
        }
        throw new InvalidDataException();
    }

    @Override
    public List<BookResponseDTO> getBooksByGenre(long genreId) {
        if (genreValidator.isExistById(genreId)) {
            List<Book> bookList = bookGenreDAO.getAllBooksByGenre(genreId).get();
            List<BookResponseDTO> bookDTOList = genreMapper.bookListToBookDTOList(bookList);
            setGenreForAllBooks(bookDTOList);
            return bookDTOList;
        }
        throw new InvalidDataException();
    }

    @Override
    public void createConnection(long bookId, long genreId) {
        bookGenreDAO.createConnection(bookId, genreId);
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
