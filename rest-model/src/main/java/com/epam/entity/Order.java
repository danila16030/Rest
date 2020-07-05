package com.epam.entity;

public class Order {
    private String orderTime;
    private float price;
    private long bookId;
    private long orderId;

    public Order() {
    }

    public Order(String orderTime, float price, long bookId, long orderId) {
        this.orderTime = orderTime;
        this.price = price;
        this.bookId = bookId;
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Float.compare(order.price, price) != 0) return false;
        if (bookId != order.bookId) return false;
        if (orderId != order.orderId) return false;
        return orderTime != null ? orderTime.equals(order.orderTime) : order.orderTime == null;
    }

    @Override
    public int hashCode() {
        int result = orderTime != null ? orderTime.hashCode() : 0;
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + (int) (bookId ^ (bookId >>> 32));
        result = 31 * result + (int) (orderId ^ (orderId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderTime='" + orderTime + '\'' +
                ", price=" + price +
                ", bookId=" + bookId +
                ", orderId=" + orderId +
                '}';
    }
}
