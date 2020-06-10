package com.epam.service;

import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;

import java.util.List;

public interface BookGenreService {
    public List<GenreDTO> getGenresByBook(String bookName);

    public List<BookDTO> getBooksByGenre(String genreName);
}
