package com.epam.services.service;

import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.dto.GenreDTO;

import java.util.List;

public interface BookGenreService {
    public List<GenreDTO> getGenresByBook(int bookId);

    public List<BookDTO> getBooksByGenre(int genreId);

    public void createConnection(int bookId,int genreId);
}
