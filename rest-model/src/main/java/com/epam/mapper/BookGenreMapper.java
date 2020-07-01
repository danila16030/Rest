package com.epam.mapper;

import com.epam.dto.responce.BookResponseDTO;
import com.epam.dto.responce.GenreResponseDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookGenreMapper {
    GenreResponseDTO genreToGenreDTO(Genre genre);

    List<GenreResponseDTO> genreListToGenreDTOList(List<Genre> genres);

    BookResponseDTO bookToBookDTO(Book book);

    List<BookResponseDTO> bookListToBookDTOList(List<Book> genres);
}
