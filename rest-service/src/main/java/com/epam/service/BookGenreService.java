package com.epam.service;

import com.epam.dto.responce.BookResponseDTO;
import com.epam.dto.responce.GenreResponseDTO;

import java.util.List;

public interface BookGenreService {
    List<GenreResponseDTO> getGenresByBook(long bookId,int limit,int offset);

    List<BookResponseDTO> getBooksByGenre(long genreId,int limit,int offset);

    void createConnection(long bookId, long genreId);
}
