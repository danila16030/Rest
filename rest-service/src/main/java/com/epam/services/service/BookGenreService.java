package com.epam.services.service;

import com.epam.models.dto.BookDTO;
import com.epam.models.dto.GenreDTO;

import java.util.List;

public interface BookGenreService {
    public List<GenreDTO> getGenresByBook(long bookId);

    public List<BookDTO> getBooksByGenre(long genreId);

    public void createConnection(long bookId,long genreId);
}
