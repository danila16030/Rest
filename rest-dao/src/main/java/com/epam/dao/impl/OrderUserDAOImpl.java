package com.epam.dao.impl;

import com.epam.dao.OrderUserDAO;
import com.epam.entity.Order;
import com.epam.entity.User;
import com.epam.exception.NoSuchElementException;
import com.epam.rowMapper.CustomerMapper;
import com.epam.rowMapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderUserDAOImpl implements OrderUserDAO {
    private JdbcTemplate jdbcTemplate;
    private static final String createConnection = "INSERT INTO order_user(user_id, order_id) VALUES (?,?)";
    private static final String getOrders = "SELECT * FROM order_user sc INNER JOIN public.order c ON " +
            "c.order_id=sc.order_id WHERE sc.user_id=? LIMIT ? OFFSET ?";
    private static final String getOrdersWithoutLimit = "SELECT * FROM order_user sc INNER JOIN public.order c ON " +
            "c.order_id=sc.order_id WHERE sc.user_id=?";
    private static final String check = "SELECT user_id FROM order_user WHERE order_id=? AND user_id=?";
    private static final String getTopUser = "SELECT r.user_id,r.username,r.totalamount " +
            "FROM (SELECT us.user_id,us.username, SUM(o.order_price) AS totalamount " +
            "FROM public.order o INNER JOIN order_user ou ON " +
            "o.order_id=ou.order_id INNER JOIN public.user us ON ou.user_id = us.user_id " +
            "GROUP BY us.user_id) r GROUP BY r.user_id, r.username,r.totalamount";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createConnection(long userId, long orderId) {
        jdbcTemplate.update(createConnection, userId, orderId);
    }

    @Override
    public Optional<List<Order>> getOrders(long userId, int limit, int offset) {
        try {
            return Optional.of(jdbcTemplate.query(getOrders, new Object[]{userId, limit, offset}, new OrderMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Optional<List<Order>> getOrders(long userId) {
        try {
            return Optional.of(jdbcTemplate.query(getOrdersWithoutLimit, new Object[]{userId}, new OrderMapper()));
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean checkConnection(long userId, long orderId) {
        try {
            jdbcTemplate.queryForObject(check, new Object[]{orderId, userId}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<User> getTopUser() {
        try {
            return jdbcTemplate.query(getTopUser, new CustomerMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            throw new NoSuchElementException();
        }
    }
}
