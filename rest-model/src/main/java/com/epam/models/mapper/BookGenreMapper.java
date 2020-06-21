package com.epam.models.mapper;

import com.epam.models.dto.BookDTO;
import com.epam.models.dto.GenreDTO;
import com.epam.models.entity.Book;
import com.epam.models.entity.Genre;
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
