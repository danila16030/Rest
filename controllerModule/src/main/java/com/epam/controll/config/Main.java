package com.epam.controll.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        String[] objects = ctx.getBeanDefinitionNames();
        System.out.println(objects);
    }
}
