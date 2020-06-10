package com.epam.service;

import com.epam.config.Config;
import com.epam.dao.BookDAO;
import com.epam.dao.impl.BookDAOImpl;
import com.epam.entyty.Book;
import com.epam.entyty.Genre;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Genre book=new Genre();
        book.setGenreName("H");
        System.out.println("og");
    }
}
