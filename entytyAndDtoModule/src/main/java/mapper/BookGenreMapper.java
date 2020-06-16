package mapper;

import dto.BookDTO;
import dto.GenreDTO;
import entyty.Book;
import entyty.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookGenreMapper {
    GenreDTO genreToGenreDTO(Genre genre);

    List<GenreDTO> genreListToGenreDTOList(List<Genre> genres);

    Genre genreDTOToGenre(GenreDTO genreDTO);

    BookDTO bookToBookDTO(Book book);

    List<BookDTO> bookListToBookDTOList(List<Book> genres);

    Book bookDTOToBook(BookDTO bookDTO);

    GenreDTO getGenreDTO(String json);
}
