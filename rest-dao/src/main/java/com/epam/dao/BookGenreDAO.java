package com.epam.dao;

import com.epam.entity.Book;
import com.epam.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookGenreDAO extends SetData {

     Optional<List<Book>> getAllBooksByGenre(long genreId);

     Optional<List<Genre>> getAllGenresOnBook(long bookId);

     void createConnection(long bookId, long genreId);
}
