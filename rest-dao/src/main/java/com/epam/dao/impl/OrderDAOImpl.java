package com.epam.dao.impl;

import com.epam.dao.OrderDAO;
import com.epam.entity.Order;
import com.epam.entity.Order_;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public Long makeAnOrder(String time, float price, long bookId) {
        Order order = new Order();
        entityManager.getTransaction().begin();
        order.setOrderTime(time);
        order.setPrice(price);
        order.setBookId(bookId);
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        return order.getOrderId();
    }

    @Override
    public Order getOrder(long orderId) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Order_.ORDER_ID), orderId));
        try {
            Order order = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return order;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Order getOrderWithoutException(long orderId) {
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, orderId);
        entityManager.getTransaction().commit();
        return order;
    }

    @Override
    public Order updateOrder(String time, float price, long bookId, long orderId) {
        Order order = new Order(time, price, bookId, orderId);
        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();
        return order;
    }

    @Override
    public void removeOrder(long orderId) {
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
        entityManager.remove(order);
        entityManager.getTransaction().commit();
    }
}
