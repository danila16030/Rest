package com.epam.dao;

import com.epam.entity.Order;

import java.util.List;

public interface OrderDAO {
    Long makeAnOrder(Order order);

    Order getOrder(long orderId);

    List<Order> getOrderByBook(long bookId);

    Order getOrderWithoutException(long orderId);

    Order updateOrder(Order order);

    void removeOrder(long orderId);

}
