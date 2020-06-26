package com.epam.validator;

import com.epam.dao.impl.BookDAOImpl;
import com.epam.dto.BookDTO;
import com.epam.dto.GenreDTO;
import com.epam.entity.Book;
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
        if (title.isEmpty() | description.isEmpty() | writingDate.isEmpty() | author.isEmpty() | title.isBlank()
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
        for (GenreDTO genre : bookDTO.getGenres()) {
            if (genre.getGenreName().isEmpty() | genre.getGenreName().isBlank()) {
                return false;
            }
        }
        if (title.isEmpty() | description.isEmpty() | writingDate.isEmpty() | author.isEmpty() | title.isBlank() |
                description.isBlank() | writingDate.isBlank() | author.isBlank() | price == 0 | numberOfPage == 0) {
            return false;
        }
        return true;
    }
}
