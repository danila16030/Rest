package com.epam.controller;

import com.epam.assembler.OrderAssembler;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateOrderDTO;
import com.epam.entity.Order;
import com.epam.model.OrderModel;
import com.epam.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAssembler orderAssembler;

    @PostMapping(headers = {"Accept=application/json"})
    public ResponseEntity<OrderModel> makeAnOrder(@RequestBody @Valid MakeAnOrderRequestDTO makeAnOrderRequestDTO) {
        Order response = orderService.makeAnOrder(makeAnOrderRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getOrderId()).build().toUri();
        response.setUserId(makeAnOrderRequestDTO.getUserId());
        return ResponseEntity.created(location).body(orderAssembler.toModel(response));
    }

   @GetMapping("{userId:[0-9]+},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<OrderModel>> getAllOrders(@PathVariable long userId,
                                                                    @PathVariable int limit,
                                                                    @PathVariable int offset) {
        return ResponseEntity.ok(orderAssembler.toCollectionModel(orderService.getOrders(userId, limit, offset), userId));
    }
    @GetMapping("{userId:[0-9]+},{orderId:[0-9]+}")
    public ResponseEntity<OrderModel> getOrder(@PathVariable long userId, @PathVariable long orderId) {
        return ResponseEntity.ok(orderAssembler.toModel(orderService.getOrder(userId, orderId)));
    }
    @DeleteMapping("{userId:[0-9]+},{orderId:[0-9]+}")
    public ResponseEntity removeOrder(@PathVariable long userId, @PathVariable long orderId) {
        orderService.removeOrder(userId, orderId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<OrderModel> update(@RequestBody @Valid UpdateOrderDTO updateOrderDTO) {
        Order response = orderService.updateOrder(updateOrderDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getOrderId()).build().toUri();
        response.setUserId(updateOrderDTO.getUserId());
        return ResponseEntity.created(location).body(orderAssembler.toModel(response));
    }
}
