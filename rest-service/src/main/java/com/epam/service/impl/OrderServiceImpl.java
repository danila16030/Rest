package com.epam.service.impl;

import com.epam.dao.OrderDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dto.request.MakeAnOrderRequestDTO;
import com.epam.dto.responce.OrderResponseDTO;
import com.epam.entity.Order;
import com.epam.exception.NoSuchElementException;
import com.epam.mapper.Mapper;
import com.epam.service.OrderService;
import com.epam.validator.OrderValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public OrderResponseDTO makeAnOrder(MakeAnOrderRequestDTO requestDTO) {
        long orderId = orderDAO.makeAnOrder(requestDTO.getOrderTime(), requestDTO.getPrice(),
                requestDTO.getBookId());
        orderUserDAO.createConnection(requestDTO.getUserId(), orderId);
        return mapper.orderToOrderDTO(new Order(requestDTO.getOrderTime(), requestDTO.getPrice(), requestDTO.getBookId(), orderId));
    }

    @Override
    public List<OrderResponseDTO> getOrders(long userId,int limit,int offset) {
        List<Order> orders = orderUserDAO.getOrders(userId,limit,offset).get();
        return mapper.orderListToOrderDTOList(orders);
    }

    @Override
    public OrderResponseDTO getOrder(long userId, long orderId) {
        if (orderValidator.isConnected(userId, orderId)) {
            return mapper.orderToOrderDTO(orderDAO.getOrder(orderId));
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
}
