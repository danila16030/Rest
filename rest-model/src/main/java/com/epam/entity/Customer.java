package com.epam.entity;

import javax.persistence.*;

@Entity
public class Customer {

    private float totalPrice;
    @Column(name = "username")
    private String username;
    @Id
    @Column(name = "user_id")
    private long userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public float getTotalPrice() {
        return totalPrice;
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
