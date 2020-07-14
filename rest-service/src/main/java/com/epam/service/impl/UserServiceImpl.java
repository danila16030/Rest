package com.epam.service.impl;

import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dao.UserDAO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.request.update.UpdateUserDTO;
import com.epam.entity.*;
import com.epam.exception.CantBeRemovedException;
import com.epam.exception.DuplicatedException;
import com.epam.exception.NoSuchElementException;
import com.epam.service.UserService;
import com.epam.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private OrderUserDAO orderUserDAO;
    private UserValidator userValidator;
    private BookDAO bookDAO;
    private BookGenreDAO bookGenreDAO;

    @Autowired
    UserServiceImpl(UserDAO userDAO, OrderUserDAO orderUserDAO, UserValidator userValidator, BookDAO bookDAO,
                    BookGenreDAO bookGenreDAO) {
        this.userDAO = userDAO;
        this.orderUserDAO = orderUserDAO;
        this.userValidator = userValidator;
        this.bookDAO = bookDAO;
        this.bookGenreDAO = bookGenreDAO;
    }

    @Override
    public User getUser(long userId) {
        User response = userDAO.getUser(userId);
        response.setOrders(getOrder(response.getUserId()));
        return response;
    }

    @Override
    public User createUser(CreateUserDTO user) {
        if (!userValidator.isExistByName(user.getUsername())) {
            return userDAO.createUser(user.getUsername());
        }
        throw new DuplicatedException("User with this name is already exist");
    }

    @Override
    public List<User> getAll(int limit, int offset) {
        List<User> response = userDAO.getAll(limit, offset).get();
        setOrder(response);
        return response;
    }

    @Override
    public void removeUser(long userId) {
        if (userValidator.isExistById(userId)) {
            if (userValidator.canBeRemoved(userId)) {
                userDAO.removeUser(userId);
                return;
            }
            throw new CantBeRemovedException("User still has orders");
        }
        throw new NoSuchElementException("User does not exist");
    }

    @Override
    public User updateUser(UpdateUserDTO updateUserDTO) {
        if (userValidator.isExistById(updateUserDTO.getUserId())) {
            return userDAO.updateUser(updateUserDTO.getUsername(), updateUserDTO.getUserId());
        }
        throw new NoSuchElementException("User does not exist");
    }

    @Override
    public User getTopUser() {
        List<Customer> response = orderUserDAO.getTopUser();
        Customer topCustomer = getTopCustomer(response);
        User user = getUser(topCustomer.getUserId());
        Map<Genre, Integer> counter = getGenreCount(user);
        Genre topGenre = getTopGenre(counter);
        user.setFavoriteGenre(topGenre);
        user.setTotalPrice(topCustomer.getTotalPrice());
        return user;
    }

    private void setOrder(List<User> users) {
        for (User user : users) {
            user.setOrders(getOrder(user.getUserId()));
        }
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

    private Map<Genre, Integer> getGenreCount(User user) {
        Map<Genre, Integer> counter = new HashMap<>();
        for (Order order : user.getOrders()) {
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
