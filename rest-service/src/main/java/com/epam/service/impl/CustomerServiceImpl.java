package com.epam.service.impl;

import com.epam.dao.BookDAO;
import com.epam.dao.BookGenreDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dao.UserDAO;
import com.epam.entity.*;
import com.epam.exception.NoSuchElementException;
import com.epam.mapper.Mapper;
import com.epam.service.CustomerService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
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

    /**
     * Return user with his orders
     * The userId argument specify user in database
     * <p>
     * This method return user with all his orders.If user doesnt exist throw NoSuchElementException.
     *
     * @param userId specified user id
     * @return user with all his orders
     * @throws NoSuchElementException
     */
    @Override
    public Customer getUser(long userId) {
        Customer response = mapper.userToCustomer(userDAO.getUser(userId));
        response.setOrders(getOrder(response.getUserId()));
        return response;
    }

    /**
     * Return customer with the highest cost of all orders and his favorite genre.
     * <p>
     * This method return customer with the highest cost of all orders.
     * And then set your favorite genre, which searched by the books he orders.
     *
     * @return customer with the highest cost of all orders and his favorite genre.
     * @see Customer
     * @see Genre
     */
    @Override
    public Customer getTopCustomer() {
        List<Customer> response = orderUserDAO.getTopUser();
        Customer topCustomer = topCustomer(response);
        topCustomer.setOrders(getOrder(topCustomer.getUserId()));
        Map<Genre, Integer> counter = getGenreCount(topCustomer);
        Genre topGenre = getTopGenre(counter);
        topCustomer.setFavoriteGenre(topGenre);
        topCustomer.setOrders(null);
        return topCustomer;
    }

    /**
     * Return the customer with the biggest amount of total price.
     * The customer argument is a list that contain customers
     * <p>
     * This method return customer with the biggest amount of total price if some customers have the same amount of
     * total price first one will be returned
     *
     * @param customers list of customer
     * @return customer with the biggest amount of total price
     * @see Customer
     */
    private Customer topCustomer(List<Customer> customers) {
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

    /**
     * Return map that contains information about the amount of each genre
     * The customer argument is an object that contain information about customer and his orders.
     * <p>
     * This method counts the number of each genre and returns a map, which key is the genre and the value
     * of how many times the genre was encountered.
     *
     * @param customer the list of books
     * @return map that contain information about amount of each genre
     * @see Book
     * @see Genre
     * @see Customer
     * @see Order
     */
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

    /**
     * Return the most common genre
     * The count argument is a map that contain information about amount of each genre
     * <p>
     * This method return the most common genre found from map.
     * If some genres have equal number the most common genre will be the first one.
     *
     * @param count map that contain information about amount of each genre
     * @return the most common genre
     * @see Genre
     */
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

    /**
     * Return list of user orders
     * The userId argument specify user in database
     * <p>
     * This method return list of user orders.If user doesnt have orders return empty list. If user doesnt exist
     * throw NoSuchElementException.
     *
     * @param userId specified user id
     * @return list of user orders
     * @throws NoSuchElementException
     */
    private List<Order> getOrder(long userId) {
        return orderUserDAO.getOrders(userId).get();
    }

}
