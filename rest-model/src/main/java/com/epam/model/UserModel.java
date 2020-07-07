package com.epam.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class UserModel extends RepresentationModel<UserModel> {

    private String username;
    private List<OrderModel> orders;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
}
