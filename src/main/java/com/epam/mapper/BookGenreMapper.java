package com.epam.mapper;

import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.entyty.Book;
import com.epam.entyty.Genre;
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
