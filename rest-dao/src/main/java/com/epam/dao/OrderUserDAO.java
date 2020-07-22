package com.epam.dao;

import com.epam.entity.Customer;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;

import java.util.List;
import java.util.Optional;

public interface OrderUserDAO {
    void createConnection(OrderUser orderUser);

    Optional<List<Order>> getOrders(long userId, int limit, int offset);

    Optional<List<Order>> getOrders(long userId);

    boolean checkConnection(OrderUser orderUser);

    List<Customer> getTopUser();
}
