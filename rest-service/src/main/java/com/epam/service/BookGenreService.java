package com.epam.service;

import com.epam.entity.Genre;

import java.util.List;

public interface BookGenreService {
    List<Genre> getGenresByBook(long bookId, int limit, int offset);

    List<com.epam.entity.Book> getBooksByGenre(long genreId, int limit, int offset);


}
