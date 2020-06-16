package service;

import dto.BookDTO;
import dto.GenreDTO;

import java.util.List;

public interface BookGenreService {
    public List<GenreDTO> getGenresByBook(String bookName);

    public List<BookDTO> getBooksByGenre(String genreName);

    public void createConnection(int bookId,int genreId);
}
