package com.epam.service;

import com.epam.dto.request.MakeAnOrderRequestDTO;
import com.epam.dto.responce.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO makeAnOrder(MakeAnOrderRequestDTO makeAnOrderRequestDTO);
    List<OrderResponseDTO> getOrders(long userId,int limit,int offset);
    OrderResponseDTO getOrder(long userId,long orderId);
    void removeOrder(long userId,long orderId);
}
