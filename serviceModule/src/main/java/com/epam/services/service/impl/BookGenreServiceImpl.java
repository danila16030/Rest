package com.epam.services.service.impl;

import com.epam.daos.dao.impl.BookGenreDAOImpl;
import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.dto.GenreDTO;
import com.epam.entytys.entyty.Book;
import com.epam.entytys.entyty.Genre;
import com.epam.entytys.mapper.BookGenreMapper;
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
    public List<GenreDTO> getGenresByBook(int bookId) {
        List<Genre> genres = bookGenreDAO.getAllGenresOnBook(bookId).get();
        return bookGenreMapper.genreListToGenreDTOList(genres);
    }

    @Override
    public List<BookDTO> getBooksByGenre(int genreId) {
        List<Book>books = bookGenreDAO.getAllBooksByGenre(genreId).get();
        return bookGenreMapper.bookListToBookDTOList(books);
    }

    @Override
    public void createConnection(int bookId, int genreId) {
        bookGenreDAO.createConnection(bookId, genreId);
    }
}
