package com.epam.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "public.user")
public class User {
    @Column(name = "username", nullable = false)
    private String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Transient
    private float totalPrice;
    @Transient
    private Genre favoriteGenre;
    @Transient
    private List<Order> orders;


    public User() {
    }

    public User(String username, long userId) {
        this.username = username;
        this.userId = userId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public Genre getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(Genre favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return orders != null ? orders.equals(user.orders) : user.orders == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + username + '\'' +
                ", orders=" + orders +
                ", id=" + userId +
                '}';
    }
}
