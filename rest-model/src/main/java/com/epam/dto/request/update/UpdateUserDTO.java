package com.epam.dto.request.update;

import com.epam.entity.Order;

import javax.validation.constraints.*;
import java.util.List;

public class UpdateUserDTO {
    @Size(min = 2, max = 30, message = "Incorrect username(Should have size 2-30)")
    @Pattern(regexp = "[a-zA-Z 0-9]+",
            message = "Incorrect username (Should contain Latin letters, spaces or numbers)")
    @NotBlank(message = "Incorrect username(Should contain not only spaces)")
    @NotEmpty(message = "Incorrect username(Should contain some information)")
    private String username;
    @NotEmpty(message = "Incorrect order list(Should contain some information)")
    private List<Order> orders;
    @Min(value = 1, message = "Incorrect id value (Value must be more then 0)")
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
