package com.epam.dao;

import com.epam.entyty.Book;
import com.epam.entyty.Genre;

import javax.sql.DataSource;
import java.util.List;

public interface BookGenreDAO extends SetData {

    public List<Book> getAllBooksByGenre(int genreId);

    public List<Genre> getAllGenresOnBook(int bookId);

    public void createNew(int bookId,int genreId);
}
