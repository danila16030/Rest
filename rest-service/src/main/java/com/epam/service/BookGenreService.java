package com.epam.service;

import com.epam.entity.Book;
import com.epam.entity.Genre;

import java.util.List;

public interface BookGenreService {
    List<Genre> getGenresByBook(long bookId, int limit, int offset);

    List<Book> getBooksByGenre(long genreId, int limit, int offset);
}
