package com.epam.service.impl;

import com.epam.dao.OrderDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateOrderDTO;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;
import com.epam.exception.NoSuchElementException;
import com.epam.mapper.Mapper;
import com.epam.service.OrderService;
import com.epam.validator.OrderValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderUserDAO orderUserDAO;
    private OrderDAO orderDAO;
    private OrderValidator orderValidator;
    private final Mapper mapper = Mappers.getMapper(Mapper.class);

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, OrderUserDAO orderUserDAO, OrderValidator orderValidator) {
        this.orderDAO = orderDAO;
        this.orderUserDAO = orderUserDAO;
        this.orderValidator = orderValidator;
    }

    @Override
    public Order makeAnOrder(MakeAnOrderRequestDTO requestDTO) {
        long orderId = orderDAO.makeAnOrder(mapper.orderDTOtOrder(requestDTO));
        orderUserDAO.createConnection(new OrderUser(requestDTO.getUserId(), orderId));
        return new Order(requestDTO.getOrderTime(), requestDTO.getPrice(), requestDTO.getBookId(), orderId, requestDTO.getUserId());
    }

    @Override
    public Order updateOrder(UpdateOrderDTO requestDTO) {
        if (orderValidator.isConnected(new OrderUser(requestDTO.getUserId(), requestDTO.getOrderId()))) {
            Order order = orderDAO.updateOrder(mapper.orderDTOtOrder(requestDTO));
            order.setUserId(requestDTO.getUserId());
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
    public Order getOrder(OrderUser orderUser) {
        if (orderValidator.isConnected(orderUser)) {
            Order order = orderDAO.getOrder(orderUser.getOrderId());
            order.setUserId(orderUser.getUserId());
            return order;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void removeOrder(OrderUser orderUser) {
        if (orderValidator.isExist(orderUser.getOrderId()) && orderValidator.isConnected(orderUser)) {
            orderDAO.removeOrder(orderUser.getOrderId());
        }
        throw new NoSuchElementException();
    }

    private void setId(List<Order> orders, long userId) {
        for (Order order : orders) {
            order.setUserId(userId);
        }
    }
}
