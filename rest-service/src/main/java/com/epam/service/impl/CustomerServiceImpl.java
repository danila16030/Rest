package com.epam.service.impl;

import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dao.UserDAO;
import com.epam.entity.*;
import com.epam.mapper.Mapper;
import com.epam.service.CustomerService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    private BookDAO bookDAO;
    private BookGenreDAO bookGenreDAO;
    private OrderUserDAO orderUserDAO;
    private UserDAO userDAO;

    @Autowired
    public CustomerServiceImpl(BookDAO bookDAO, BookGenreDAO bookGenreDAO, OrderUserDAO orderUserDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.bookGenreDAO = bookGenreDAO;
        this.orderUserDAO = orderUserDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Customer getUser(long userId) {
        Customer response = mapper.userToCustomer(userDAO.getUser(userId));
        response.setOrders(getOrder(response.getUserId()));
        return response;
    }


    @Override
    public Customer getTopCustomer() {
        List<Customer> response = orderUserDAO.getTopUser();
        Customer topCustomer = getTopCustomer(response);
        topCustomer.setOrders(getOrder(topCustomer.getUserId()));
        Map<Genre, Integer> counter = getGenreCount(topCustomer);
        Genre topGenre = getTopGenre(counter);
        topCustomer.setFavoriteGenre(topGenre);
        topCustomer.setOrders(null);
        return topCustomer;
    }


    private Customer getTopCustomer(List<Customer> customers) {
        Customer topCustomer = new Customer();
        float highestAmount = 0;
        for (Customer customer : customers) {
            if (highestAmount < customer.getTotalPrice()) {
                highestAmount = customer.getTotalPrice();
                topCustomer = customer;
            }
        }
        return topCustomer;
    }

    private Map<Genre, Integer> getGenreCount(Customer customer) {
        Map<Genre, Integer> counter = new HashMap<>();
        for (Order order : customer.getOrders()) {
            Book book = bookDAO.getBookById(order.getBookId());
            book.setGenres(bookGenreDAO.getAllGenresOnBook(book.getBookId()).get());
            for (Genre genre : book.getGenres()) {
                if (counter.get(genre) != null) {
                    counter.put(genre, counter.get(genre) + 1);
                } else {
                    counter.put(genre, 1);
                }
            }
        }
        return counter;
    }

    private Genre getTopGenre(Map<Genre, Integer> count) {
        AtomicInteger maxCount = new AtomicInteger();
        final Genre[] topGenre = new Genre[1];
        count.forEach((k, v) -> {
            if (maxCount.get() < v) {
                topGenre[0] = k;
                maxCount.set(v);
            }
        });
        return topGenre[0];
    }

    private List<Order> getOrder(long userId) {
        return orderUserDAO.getOrders(userId).get();
    }

}
