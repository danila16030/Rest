package com.epam.services.validator;

import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.models.dto.BookDTO;
import com.epam.models.dto.GenreDTO;
import com.epam.models.entity.Book;
import com.epam.models.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {
    @Autowired
    private BookDAOImpl bookDAO;

    public boolean isExist(BookDTO bookDTO) {
        Book book = bookDAO.getBookById(bookDTO.getBookId());
        if (book.getTitle() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidForUpdate(BookDTO bookDTO) {
        String title = bookDTO.getTitle();
        String description = bookDTO.getDescription();
        String writingDate = bookDTO.getWritingDate();
        String author = bookDTO.getAuthor();
        float price = bookDTO.getPrice();
        int numberOfPage = bookDTO.getNumberOfPages();
        long bookId = bookDTO.getBookId();
        if (title.isEmpty() | description.isEmpty() | writingDate.isEmpty() | author.isEmpty()|title.isBlank()
                | description.isBlank() | writingDate.isBlank() | author.isBlank() | price == 0 |
                numberOfPage == 0 | bookId == 0) {
            return false;
        }
        return true;
    }

    public boolean isValidForCreate(BookDTO bookDTO) {
        String title = bookDTO.getTitle();
        String description = bookDTO.getDescription();
        String writingDate = bookDTO.getWritingDate();
        String author = bookDTO.getAuthor();
        float price = bookDTO.getPrice();
        int numberOfPage = bookDTO.getNumberOfPages();
        GenreDTO genre = bookDTO.getGenre();
        String genreName = genre.getGenreName();
        if (title.isEmpty() | description.isEmpty() | writingDate.isEmpty() | author.isEmpty() |genreName.isBlank() |
                title.isBlank() | description.isBlank() | writingDate.isBlank() | author.isBlank() |
                genreName.isBlank() |price == 0 | numberOfPage == 0) {
            return false;
        }
        return true;
    }
}
