package com.epam.dao.impl;

import com.epam.dao.BaseDAO;
import com.epam.dao.OrderUserDAO;
import com.epam.entity.*;
import com.epam.exception.NoSuchElementException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderUserDAOImpl extends BaseDAO<OrderUser> implements OrderUserDAO {

    @Override
    public void createConnection(OrderUser orderUser) {
        create(orderUser);
    }

    @Override
    public Optional<List<Order>> getOrders(long userId, int limit, int offset) {
        limit = limiting(limit);
        Query query = entityManager.createNamedQuery(Order.QueryNames.FIND_ORDERS, Order.class);
        query.setParameter("userId", userId);
        try {
            return Optional.of(query.setFirstResult(offset).
                    setMaxResults(limit).getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Order>> getOrders(long userId) {
        Query query = entityManager.createNamedQuery(Order.QueryNames.FIND_ORDERS, Order.class);
        query.setParameter("userId", userId);
        try {
            return Optional.of(query.getResultList());
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean checkConnection(OrderUser orderUser) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderUser> criteria = builder.createQuery(OrderUser.class);
        Root<OrderUser> root = criteria.from(OrderUser.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(OrderUser_.USER_ID), orderUser.getUserId())).where(builder.equal(
                root.get(OrderUser_.ORDER_ID), orderUser.getOrderId()));
        try {
            OrderUser result = entityManager.createQuery(criteria).getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public List<Customer> getCustomers() {
        Query query = entityManager.createNamedQuery(Customer.QueryNames.GET_CUSTOMER, Customer.class);
        try {
            return (List<Customer>) query.getResultList();
        } catch (NoResultException e) {
            throw new NoSuchElementException();
        }
    }
}
