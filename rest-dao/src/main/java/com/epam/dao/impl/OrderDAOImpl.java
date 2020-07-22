package com.epam.dao.impl;

import com.epam.dao.BaseDAO;
import com.epam.dao.OrderDAO;
import com.epam.entity.Order;
import com.epam.entity.Order_;
import com.epam.exception.NoSuchElementException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class OrderDAOImpl extends BaseDAO<Order> implements OrderDAO {

    @Override
    public Long makeAnOrder(Order order) {
        return create(order).getOrderId();
    }

    @Override
    public Order getOrder(long orderId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Order_.ORDER_ID), orderId));
        try {
            return entityManager.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Order getOrderWithoutException(long orderId) {
        return entityManager.find(Order.class, orderId);
    }

    @Override
    public Order updateOrder(Order order) {
        return update(order);
    }

    @Override
    public void removeOrder(long orderId) {
        remove(orderId, Order.class);
    }
}
