package com.epam.dao.impl;

import com.epam.dao.OrderUserDAO;
import com.epam.entity.Customer;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;
import com.epam.exception.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate;
    private static final String getTopUser = "SELECT r.user_id,r.username,r.totalamount " +
            "FROM (SELECT us.user_id,us.username, SUM(o.order_price) AS totalamount " +
            "FROM public.order o INNER JOIN order_user ou ON " +
            "o.order_id=ou.order_id INNER JOIN public.user us ON ou.user_id = us.user_id " +
            "GROUP BY us.user_id) r GROUP BY r.user_id, r.username,r.totalamount";

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
        criteria.where(builder.equal(root.get("userId"), userId)).where(builder.equal(root.get("orderId"), orderId));
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
        Query query = entityManager.createNativeQuery("SELECT r.user_id,r.username,r.totalPrice " +
                "FROM (SELECT us.user_id,us.username, SUM(o.order_price) AS totalPrice " +
                "FROM public.order o INNER JOIN order_user ou ON " +
                "o.order_id=ou.order_id INNER JOIN public.user us ON ou.user_id = us.user_id " +
                "GROUP BY us.user_id) r GROUP BY r.user_id, r.username,r.totalPrice", Customer.class);
        try {
            List<Customer> customers = query.getResultList();
            entityManager.getTransaction().commit();
            return customers;
        } catch (NoResultException e) {
            entityManager.getTransaction().commit();
            throw new NoSuchElementException();
        }
          /* Query query = entityManager.createQuery("SELECT r.userId,r.username,r.totalPrice " +
                "FROM User r WHERE r IN (SELECT us.userId,us.username, SUM(o.price) AS totalPrice " +
                "FROM Order o INNER JOIN order_user ou ON " +
                "o.orderId=ou.orderId INNER JOIN User us ON ou.userId = us.userId " +
                "GROUP BY us.userId) GROUP BY r.userId, r.username,r.totalamount", User.class);*/
    }
}
