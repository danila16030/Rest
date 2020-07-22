package com.epam.validator;

import com.epam.dao.OrderUserDAO;
import com.epam.dao.UserDAO;
import com.epam.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidator {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private OrderUserDAO orderUserDAO;

    public boolean isExistByName(String username) {
        if (userDAO.getUserByNameWithoutException(username).getUsername() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isExistById(long userId) {
        if (userDAO.getUserByIdWithoutException(userId).getUsername() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canBeRemoved(long userId) {
        List<Order> orders = orderUserDAO.getOrders(userId).get();
        if (orders.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
