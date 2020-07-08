package com.epam.model;

import org.springframework.hateoas.RepresentationModel;

public class CustomerModel extends RepresentationModel<CustomerModel> {
    private float totalPrice;
    private String username;
    private long userId;
    private GenreModel favoriteGenre;

    public GenreModel getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(GenreModel favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
