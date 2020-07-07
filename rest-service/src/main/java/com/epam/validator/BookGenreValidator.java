package com.epam.validator;

import com.epam.dao.BookGenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookGenreValidator {
    @Autowired
    BookGenreDAO bookGenreDAO;

    public boolean isConnected(long bookId, long genreId) {
        return bookGenreDAO.checkConnection(bookId, genreId);
    }
}
