package com.epam.service.impl;

import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dao.UserDAO;
import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.request.update.UpdateUserDTO;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.entity.Order;
import com.epam.entity.User;
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
        List<User> response = userDAO.getAll(limit, offset);
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
        List<User> response = orderUserDAO.getTopUser();
        User topUser = getTopCustomer(response);
        User user = getUser(topUser.getUserId());
        Map<Genre, Integer> counter = getGenreCount(user);
        Genre topGenre = getTopGenre(counter);
        topUser.setFavoriteGenre(topGenre);
        return topUser;
    }

    private void setOrder(List<User> users) {
        for (User user : users) {
            user.setOrders(getOrder(user.getUserId()));
        }
    }

    private User getTopCustomer(List<User> customers) {
        User topCustomer = new User();
        float highestAmount = 0;
        for (User customer : customers) {
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
        int maxCount = 0;
        final Genre[] topGenre = new Genre[1];
        count.forEach((k, v) -> {
            if (maxCount < v) {
                topGenre[0] = k;
            }
        });
        return topGenre[0];
    }

    private List<Order> getOrder(long userId) {
        return orderUserDAO.getOrders(userId).get();
    }
}
