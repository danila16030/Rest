package com.epam.service;

import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateOrderDTO;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;

import java.util.List;

public interface OrderService {
    Order makeAnOrder(MakeAnOrderRequestDTO makeAnOrderRequestDTO);
    Order updateOrder(UpdateOrderDTO updateOrderDTO);
    List<Order> getOrders(long userId,int limit,int offset);
    Order getOrder(OrderUser orderUser);
    void removeOrder(OrderUser orderUser);
}
