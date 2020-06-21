package com.epam.services.service.impl;

import com.epam.daos.dao.impl.BookGenreDAOImpl;
import com.epam.models.dto.BookDTO;
import com.epam.models.dto.GenreDTO;
import com.epam.models.entity.Book;
import com.epam.models.entity.Genre;
import com.epam.models.mapper.BookGenreMapper;
import com.epam.services.service.BookGenreService;
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
        List<Book>books = bookGenreDAO.getAllBooksByGenre(genreId).get();
        return bookGenreMapper.bookListToBookDTOList(books);
    }

    @Override
    public void createConnection(long bookId, long genreId) {
        bookGenreDAO.createConnection(bookId, genreId);
    }
}
