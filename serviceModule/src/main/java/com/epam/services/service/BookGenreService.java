package com.epam.services.service;

import com.epam.entytys.dto.BookDTO;
import com.epam.entytys.dto.GenreDTO;

import java.util.List;

public interface BookGenreService {
    public List<GenreDTO> getGenresByBook(String bookName);

    public List<BookDTO> getBooksByGenre(String genreName);

    public void createConnection(int bookId,int genreId);
}
