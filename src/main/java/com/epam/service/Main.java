package com.epam.service;

import com.epam.config.Config;
import com.epam.dao.GenreDAO;
import com.epam.dao.impl.GenreDAOImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        GenreDAOImpl dao = (GenreDAOImpl) context.getBean(GenreDAO.class);
        System.out.println(dao.removeGenre("horror2"));
    }
}
