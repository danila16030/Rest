package com.epam.mapper;

import com.epam.dto.BookDTO;
import com.epam.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = GenreMapper.class)
public interface BookMapper {
    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);

    BookDTO bookToBookDTO(Book book);

    List<BookDTO> bookListToBookDTOList(List<Book> genres);
}
