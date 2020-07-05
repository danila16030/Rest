package com.epam.rowMapper;

import com.epam.dao.impl.fields.OrderFields;
import com.epam.entity.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setBookId(resultSet.getLong(OrderFields.BOOK));
        order.setOrderId(resultSet.getLong(OrderFields.ID));
        order.setPrice(resultSet.getFloat(OrderFields.PRICE));
        order.setOrderTime(resultSet.getString(OrderFields.TIME));
        return order;
    }
}
