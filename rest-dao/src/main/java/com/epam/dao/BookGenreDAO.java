package com.epam.dao;

import com.epam.entity.Book;
import com.epam.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface BookGenreDAO extends SetData {

    Optional<List<Book>> getAllBooksByGenre(long genreId, int limit, int offset);

    Optional<List<Genre>> getAllGenresOnBook(long bookId, int limit, int offset);

    Optional<List<Genre>> getAllGenresOnBook(long bookId);

    void createConnection(long bookId, long genreId);
}
