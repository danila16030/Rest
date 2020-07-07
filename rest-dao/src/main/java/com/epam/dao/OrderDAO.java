package com.epam.dao;

import com.epam.entity.Order;

public interface OrderDAO extends SetData {
    Long makeAnOrder(String time, float price, long bookId);

    Order getOrder(long orderId);

    Order getOrderWithoutException(long orderId);

    Order updateOrder(String time, float price, long bookId,long orderId);

    void removeOrder(long orderId);

}
