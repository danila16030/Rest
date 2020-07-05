package com.epam.controller;

import com.epam.dto.request.MakeAnOrderRequestDTO;
import com.epam.dto.responce.OrderResponseDTO;
import com.epam.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(headers = {"Accept=application/json"})
    public ResponseEntity<OrderResponseDTO> makeAnOrder(@RequestBody @Valid MakeAnOrderRequestDTO makeAnOrderRequestDTO) {
        OrderResponseDTO response = orderService.makeAnOrder(makeAnOrderRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getOrderId()).build().toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("{id:[0-9]+},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@PathVariable long id,
                                                               @PathVariable("limit") int limit,
                                                               @PathVariable int offset) {
        return ResponseEntity.ok(orderService.getOrders(id,limit,offset));
    }

    @GetMapping("{userId:[0-9]+},{orderId:[0-9]+}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable long userId, @PathVariable long orderId) {
        return ResponseEntity.ok(orderService.getOrder(userId, orderId));
    }

    @DeleteMapping("{userId:[0-9]+},{orderId:[0-9]+}")
    public ResponseEntity removeOrder(@PathVariable long userId, @PathVariable long orderId) {
        orderService.removeOrder(userId, orderId);
        return ResponseEntity.noContent().build();
    }
}
