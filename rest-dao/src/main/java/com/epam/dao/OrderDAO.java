package com.epam.dao;

import com.epam.entity.Order;

public interface OrderDAO{
    Long makeAnOrder(Order order);

    Order getOrder(long orderId);

    Order getOrderWithoutException(long orderId);

    Order updateOrder(Order order);

    void removeOrder(long orderId);

}
