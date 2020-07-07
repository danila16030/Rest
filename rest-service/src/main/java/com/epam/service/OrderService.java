package com.epam.service;

import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateOrderDTO;
import com.epam.entity.Order;

import java.util.List;

public interface OrderService {
    Order makeAnOrder(MakeAnOrderRequestDTO makeAnOrderRequestDTO);
    Order updateOrder(UpdateOrderDTO updateOrderDTO);
    List<Order> getOrders(long userId,int limit,int offset);
    Order getOrder(long userId, long orderId);
    void removeOrder(long userId,long orderId);
}
