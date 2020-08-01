package com.epam.service.impl;

import com.epam.dao.OrderDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateOrderRequestDTO;
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


    /**
     * Returns the created order
     * The requestDTO argument must contain information about the order being created.
     * <p>
     * This method return order that was created.And create connection between order and user that make order
     *
     * @param requestDTO object that contain information to be used when creating a order
     * @return the order that was created
     * @see Order
     * @see MakeAnOrderRequestDTO
     */
    @Override
    public Order makeAnOrder(MakeAnOrderRequestDTO requestDTO) {
        long orderId = orderDAO.makeAnOrder(mapper.orderDTOtOrder(requestDTO));
        orderUserDAO.createConnection(new OrderUser(requestDTO.getUserId(), orderId));
        return new Order(requestDTO.getOrderTime(), requestDTO.getPrice(), requestDTO.getBookId(), orderId, requestDTO.getUserId());
    }

    /**
     * Returns the updated order
     * The requestDTO argument must contain information about the order being updated.
     * <p>
     * This method return order that was updated if user that wanna update this order connected with this order.
     * If order doesn't exist or user doesnt connected with this order throw exception NoSuchElementException
     *
     * @param requestDTO object that contain information to be used when updating
     * @return the order that was updated
     * @throws NoSuchElementException
     * @see Order
     * @see UpdateOrderRequestDTO
     */
    @Override
    public Order updateOrder(UpdateOrderRequestDTO requestDTO, long userId) {
        if (orderValidator.isConnected(new OrderUser(userId, requestDTO.getOrderId()))) {
            Order order = orderDAO.updateOrder(mapper.orderDTOtOrder(requestDTO));
            order.setUserId(userId);
            return order;
        }
        throw new NoSuchElementException();
    }

    /**
     * Returns a list of user orders
     * The userId argument specify user in database
     * The limit argument specify maximum size of list
     * The offset argument specify offset from the beginning in database*
     * <p>
     * This method return list of user orders. If user doesn't have any orders return empty list
     * If user doesn't exist throw NoResultException
     *
     * @param userId specified user name
     * @param limit  the maximum number of books in list
     * @param offset the offset in database from beginning
     * @return the list of user orders
     * @throws NoSuchElementException
     * @see Order
     */
    @Override
    public List<Order> getOrders(long userId, int limit, int offset) {
        List<Order> orders = orderUserDAO.getOrders(userId, limit, offset).get();
        setId(orders, userId);
        return orders;
    }

    /**
     * Returns an Order object by order id
     * The username argument specify user in database
     * <p>
     * This method return specified order from database if user that wanna remove this order connected with this order.
     * If order doesn't exist or user doesnt connected with this order throw exception NoSuchElementException
     *
     * @param orderUser specified user id and order id
     * @return the order at the specified id
     * @throws NoSuchElementException
     * @see Order
     * @see OrderUser
     */
    @Override
    public Order getOrder(OrderUser orderUser) {
        if (orderValidator.isConnected(orderUser)) {
            Order order = orderDAO.getOrder(orderUser.getOrderId());
            order.setUserId(orderUser.getUserId());
            return order;
        }
        throw new NoSuchElementException();
    }

    /**
     * Remove order from database.
     * The orderUser argument specify order in database and user that make order
     * <p>
     * This method remove specified order from database if user that wanna remove this order connected with this order.
     * If order doesn't exist or user doesnt connected with this order throw exception NoSuchElementException
     *
     * @param orderUser specified order id and user id
     * @throws NoSuchElementException
     * @see OrderUser
     */
    @Override
    public void removeOrder(OrderUser orderUser) {
        if (orderValidator.isExist(orderUser.getOrderId()) && orderValidator.isConnected(orderUser)) {
            orderDAO.removeOrder(orderUser.getOrderId());
        }
        throw new NoSuchElementException();
    }

    /**
     * Set user id for each order in list
     * The userId argument specify user
     * The orders argument is a list of user orders
     * <p>
     * This method set user id to each order in order list.
     *
     * @param orders list of user orders
     * @param userId specified user id
     */
    private void setId(List<Order> orders, long userId) {
        for (Order order : orders) {
            order.setUserId(userId);
        }
    }
}
