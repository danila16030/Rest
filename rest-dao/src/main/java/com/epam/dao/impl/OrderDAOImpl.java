package com.epam.dao.impl;

import com.epam.dao.OrderDAO;
import com.epam.dao.impl.fields.OrderFields;
import com.epam.entity.Order;
import com.epam.exception.NoSuchElementException;
import com.epam.rowMapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String makeAnOrder = "INSERT INTO public.order (order_price, order_time, book_id) VALUES(?,?,?)";
    private static final String getOrder = "SELECT * FROM public.order WHERE order_id = ?";
    private static final String removeOrder = "DELETE FROM public.order WHERE order_id = ?";

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long makeAnOrder(String time, float price, long bookId) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(makeAnOrder, Statement.RETURN_GENERATED_KEYS);
                statement.setFloat(1, price);
                statement.setString(2, time);
                statement.setLong(3, bookId);
                return statement;
            }, keyHolder);
            Map<String, Object> keys = keyHolder.getKeys();
            return Long.parseLong(String.valueOf(keys.get(OrderFields.ID)));
        } catch (DuplicateKeyException e) {
            return 0l;
        }
    }

    @Override
    public Order getOrder(long orderId) {
        try {
            return jdbcTemplate.queryForObject(getOrder, new Object[]{orderId}, new OrderMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Order getOrderWithoutException(long orderId) {
        try {
            return jdbcTemplate.queryForObject(getOrder, new Object[]{orderId}, new OrderMapper());
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            return new Order();
        }
    }

    @Override
    public void removeOrder(long orderId) {
        jdbcTemplate.update(removeOrder, orderId);
    }
}
