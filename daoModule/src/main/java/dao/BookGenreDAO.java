package dao;

import entyty.Book;
import entyty.Genre;

import java.util.List;
import java.util.Optional;

public interface BookGenreDAO extends SetData {

    public Optional<List<Book>> getAllBooksByGenre(int genreId);

    public Optional<List<Genre>> getAllGenresOnBook(int bookId);

    public void createConnection(int bookId,int genreId);
}
