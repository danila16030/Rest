package com.epam.service.impl;

import com.epam.dao.impl.BookGenreDAOImpl;
import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.mapper.BookGenreMapper;
import com.epam.service.BookGenreService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookGenreServiceImpl implements BookGenreService {

    private BookGenreDAOImpl bookGenreDAO;
    private BookGenreMapper bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);

    @Autowired
    public BookGenreServiceImpl(BookGenreDAOImpl bookGenreDAO) {
        this.bookGenreDAO = bookGenreDAO;
    }


    @Override
    public List<GenreDTO> getGenresByBook(long bookId) {
        List<Genre> genres = bookGenreDAO.getAllGenresOnBook(bookId).get();
        return bookGenreMapper.genreListToGenreDTOList(genres);
    }

    @Override
    public List<BookDTO> getBooksByGenre(long genreId) {
        List<Book> bookList = bookGenreDAO.getAllBooksByGenre(genreId).get();
        List<BookDTO> bookDTOList = bookGenreMapper.bookListToBookDTOList(bookList);
        setGenreForAllBooks(bookDTOList);
        return bookDTOList;
    }

    @Override
    public void createConnection(long bookId, long genreId) {
        bookGenreDAO.createConnection(bookId, genreId);
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
