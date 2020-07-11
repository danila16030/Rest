package com.epam.validator;

import com.epam.dao.OrderDAO;
import com.epam.dao.OrderUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
    @Autowired
    OrderUserDAO orderUserDAO;
    @Autowired
    OrderDAO orderDAO;

    public boolean isConnected(long userId, long orderId) {
        return orderUserDAO.checkConnection(userId, orderId);
    }

    public boolean isExist(long orderId) {
        if (orderDAO.getOrderWithoutException(orderId) == null) {
            return false;
        } else {
            return true;
        }
    }

}
