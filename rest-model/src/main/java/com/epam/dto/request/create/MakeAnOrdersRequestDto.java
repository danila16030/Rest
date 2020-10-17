package com.epam.dto.request.create;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class MakeAnOrdersRequestDto {
    @NotEmpty(message = "Incorrect orders list (Should contain some orders)")
    private List<MakeAnOrderRequestDTO> orders;

    public List<MakeAnOrderRequestDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<MakeAnOrderRequestDTO> orders) {
        this.orders = orders;
    }
}
