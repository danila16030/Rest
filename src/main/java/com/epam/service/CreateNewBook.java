package com.epam.service;

import com.epam.dao.impl.BookDAOImpl;
import com.epam.dao.impl.GenreDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CreateNewBook {
    @Autowired
    private BookDAOImpl sqlBookDAO;
    @Autowired
    private GenreDAOImpl sqlGenreDAO;

    public void createNewBook() {
        sqlBookDAO.findBook("d");
    }
}
