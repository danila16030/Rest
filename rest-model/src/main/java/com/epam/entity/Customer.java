package com.epam.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "totalPrice", nullable = false)
    private float totalPrice;
    @Transient
    private Genre favoriteGenre;
    @Transient
    private List<Order> orders;

    public Genre getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(Genre favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

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

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        return Float.compare(customer.totalPrice, totalPrice) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (totalPrice != +0.0f ? Float.floatToIntBits(totalPrice) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "totalPrice=" + totalPrice +
                '}';
    }
}
