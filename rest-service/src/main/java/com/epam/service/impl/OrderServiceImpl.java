package com.epam.service.impl;

import com.epam.dao.OrderDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateOrderDTO;
import com.epam.entity.Order;
import com.epam.exception.NoSuchElementException;
import com.epam.service.OrderService;
import com.epam.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderUserDAO orderUserDAO;
    private OrderDAO orderDAO;
    private OrderValidator orderValidator;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, OrderUserDAO orderUserDAO, OrderValidator orderValidator) {
        this.orderDAO = orderDAO;
        this.orderUserDAO = orderUserDAO;
        this.orderValidator = orderValidator;
    }

    @Override
    public Order makeAnOrder(MakeAnOrderRequestDTO requestDTO) {
        long orderId = orderDAO.makeAnOrder(requestDTO.getOrderTime(), requestDTO.getPrice(),
                requestDTO.getBookId());
        orderUserDAO.createConnection(requestDTO.getUserId(), orderId);
        return new Order(requestDTO.getOrderTime(), requestDTO.getPrice(), requestDTO.getBookId(), orderId, requestDTO.getUserId());
    }

    @Override
    public Order updateOrder(UpdateOrderDTO updateOrderDTO) {
        if (orderValidator.isConnected(updateOrderDTO.getUserId(), updateOrderDTO.getOrderId())) {
            Order order = orderDAO.updateOrder(updateOrderDTO.getOrderTime(), updateOrderDTO.getPrice(),
                    updateOrderDTO.getBookId(), updateOrderDTO.getUserId());
            order.setUserId(updateOrderDTO.getUserId());
            return order;
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<Order> getOrders(long userId, int limit, int offset) {
        List<Order> orders = orderUserDAO.getOrders(userId, limit, offset).get();
        setId(orders, userId);
        return orders;
    }

    @Override
    public Order getOrder(long userId, long orderId) {
        if (orderValidator.isConnected(userId, orderId)) {
            Order order = orderDAO.getOrder(orderId);
            order.setUserId(userId);
            return order;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void removeOrder(long userId, long orderId) {
        if (orderValidator.isExist(orderId) && orderValidator.isConnected(userId, orderId)) {
            orderDAO.removeOrder(orderId);
        }
        throw new NoSuchElementException();
    }

    private void setId(List<Order> orders, long userId) {
        for (Order order : orders) {
            order.setUserId(userId);
        }
    }
}
