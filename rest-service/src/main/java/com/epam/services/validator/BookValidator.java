package com.epam.services.validator;

import com.epam.daos.dao.impl.BookDAOImpl;
import com.epam.models.dto.BookDTO;
import com.epam.models.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {
    @Autowired
    private BookDAOImpl bookDAO;

    public boolean isExist(BookDTO bookDTO) {
        Book book = bookDAO.getBookByName(bookDTO.getTitle());
        if (book.getTitle() == null) {
            return false;
        } else {
            return true;
        }
    }
}
