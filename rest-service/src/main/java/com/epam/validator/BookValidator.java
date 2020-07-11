package com.epam.validator;

import com.epam.dao.BookDAO;
import com.epam.dto.request.create.CreateBookRequestDTO;
import com.epam.dto.request.create.CreateGenreRequestDTO;
import com.epam.dto.request.update.UpdateBookRequestDTO;
import com.epam.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {
    @Autowired
    private BookDAO bookDAO;

    public boolean isExist(long bookId) {
        Book book = bookDAO.getBookByIdWithoutException(bookId);
        if (book == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidForUpdate(UpdateBookRequestDTO bookDTO) {
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

    public boolean isValidForCreate(CreateBookRequestDTO bookDTO) {
        String title = bookDTO.getTitle();
        String description = bookDTO.getDescription();
        String writingDate = bookDTO.getWritingDate();
        String author = bookDTO.getAuthor();
        float price = bookDTO.getPrice();
        int numberOfPage = bookDTO.getNumberOfPages();
        for (CreateGenreRequestDTO genre : bookDTO.getGenres()) {
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
