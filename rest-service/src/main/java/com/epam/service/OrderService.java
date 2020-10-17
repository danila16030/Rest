package com.epam.service;

import com.epam.dto.request.create.MakeAnOrdersRequestDto;
import com.epam.dto.request.update.UpdateOrderRequestDTO;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;

import java.util.List;

public interface OrderService {
    List<Order> makeAnOrder(MakeAnOrdersRequestDto makeAnOrderRequestDTO, long userId);

    Order updateOrder(UpdateOrderRequestDTO updateOrderRequestDTO, long userId);

    List<Order> getOrders(long userId, int limit, int offset);

    Order getOrder(OrderUser orderUser);

    void removeOrder(OrderUser orderUser);
}
