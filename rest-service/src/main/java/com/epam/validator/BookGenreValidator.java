package com.epam.validator;

import com.epam.dao.BookGenreDAO;
import com.epam.entity.BookGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookGenreValidator {
    @Autowired
    private BookGenreDAO bookGenreDAO;

    public boolean isConnected(BookGenre bookGenre) {
        return bookGenreDAO.checkConnection(bookGenre);
    }
}
