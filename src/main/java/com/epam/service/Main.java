package com.epam.service;

import com.epam.config.Config;
import com.epam.dao.BookDAO;
import com.epam.dao.GenreDAO;
import com.epam.dao.impl.BookDAOImpl;
import com.epam.dao.impl.GenreDAOImpl;
import com.epam.entyty.Book;
import com.epam.entyty.Genre;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        GenreDAOImpl dao = (GenreDAOImpl) context.getBean(GenreDAO.class);
        List<Genre> f=dao.getGenreList();
        System.out.println("og");
    }
}
