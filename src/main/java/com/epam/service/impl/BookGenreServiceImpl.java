package com.epam.service.impl;

import com.epam.dao.impl.BookDAOImpl;
import com.epam.dao.impl.BookGenreDAOImpl;
import com.epam.dao.impl.GenreDAOImpl;
import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.entyty.Book;
import com.epam.entyty.Genre;
import com.epam.mapper.BookMapper;
import com.epam.mapper.GenreMapper;
import com.epam.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookGenreServiceImpl implements BookGenreService {
    private GenreDAOImpl genreDAO;
    private GenreMapper genreMapper;
    private BookDAOImpl bookDAO;
    private BookMapper bookMapper;
    private BookGenreDAOImpl bookGenreDAO;

    @Autowired
    public BookGenreServiceImpl(BookDAOImpl bookDAO, BookMapper bookMapper, GenreMapper genreMapper,
                                GenreDAOImpl genreDAO, BookGenreDAOImpl bookGenreDAO) {
        this.bookDAO = bookDAO;
        this.bookMapper = bookMapper;
        this.genreDAO = genreDAO;
        this.genreMapper = genreMapper;
        this.bookGenreDAO = bookGenreDAO;
    }


    @Override
    public List<GenreDTO> getGenresByBook(String bookName) {
        int bookId = bookDAO.getBookByName(bookName).getBookId();
        List<Genre> genres = bookGenreDAO.getAllGenresOnBook(bookId);
        ArrayList<GenreDTO> dtoList = new ArrayList<>();
        for (Genre genre : genres) {
            dtoList.add(genreMapper.toDto(genre));
        }
        return dtoList;

    }

    @Override
    public List<BookDTO> getBooksByGenre(String genreName) {
        int genreId = genreDAO.getGenreByName(genreName).getGenreId();
        List<Book> books = bookGenreDAO.getAllBooksByGenre(genreId);
        ArrayList<BookDTO> dtoList = new ArrayList<>();
        for (Book book : books) {
            dtoList.add(bookMapper.toDto(book));
        }
        return dtoList;
    }
}
