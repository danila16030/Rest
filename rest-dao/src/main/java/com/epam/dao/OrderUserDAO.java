package com.epam.dao;

import com.epam.entity.Customer;
import com.epam.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderUserDAO {
    void createConnection(long userId, long orderId);

    Optional<List<Order>> getOrders(long userId, int limit, int offset);

    Optional<List<Order>> getOrders(long userId);

    boolean checkConnection(long userId, long orderId);

    List<Customer> getTopUser();
}
