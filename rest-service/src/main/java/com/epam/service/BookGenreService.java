package com.epam.service;

import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;

import java.util.List;

public interface BookGenreService {
    public List<GenreDTO> getGenresByBook(long bookId);

    public List<BookDTO> getBooksByGenre(long genreId);

    public void createConnection(long bookId,long genreId);
}
