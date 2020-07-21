package com.epam.dao.impl;

import com.epam.dao.OrderUserDAO;
import com.epam.entity.Customer;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;
import com.epam.entity.OrderUser_;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderUserDAOImpl implements OrderUserDAO {
    private static final String getTopUser = "SELECT r.user_id,r.username,r.totalPrice " +
            "FROM (SELECT us.user_id,us.username, SUM(o.order_price) AS totalPrice " +
            "FROM public.order o INNER JOIN order_user ou ON " +
            "o.order_id=ou.order_id INNER JOIN public.user us ON ou.user_id = us.user_id " +
            "GROUP BY us.user_id) r GROUP BY r.user_id, r.username,r.totalPrice";

    @Autowired
    EntityManager entityManager;


    @Override
    public void createConnection(long userId, long orderId) {
        OrderUser bookGenre = new OrderUser(userId, orderId);
        entityManager.getTransaction().begin();
        entityManager.persist(bookGenre);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<List<Order>> getOrders(long userId, int limit, int offset) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT o FROM OrderUser ou INNER JOIN Order o ON " +
                "o.orderId=ou.orderId WHERE ou.userId=" + userId, Order.class);
        try {
            Optional<List<Order>> orders = Optional.of(query.setFirstResult(offset).
                    setMaxResults(limit).getResultList());
            entityManager.getTransaction().commit();
            return orders;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Order>> getOrders(long userId) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT o FROM OrderUser ou INNER JOIN Order o ON " +
                "o.orderId=ou.orderId WHERE ou.userId=" + userId, Order.class);
        try {
            Optional<List<Order>> orders = Optional.of(query.getResultList());
            entityManager.getTransaction().commit();
            return orders;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean checkConnection(long userId, long orderId) {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderUser> criteria = builder.createQuery(OrderUser.class);
        Root<OrderUser> root = criteria.from(OrderUser.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(OrderUser_.USER_ID), userId)).where(builder.equal(
                root.get(OrderUser_.ORDER_ID), orderId));
        try {
            OrderUser result = entityManager.createQuery(criteria).getSingleResult();
            entityManager.getTransaction().commit();
            return true;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            return false;
        }
    }

    @Override
    public List<Customer> getTopUser() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery(getTopUser, Customer.class);
        try {
            List<Customer> customers = query.getResultList();
            entityManager.getTransaction().commit();
            return customers;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
    }
}
