package com.epam.controller;

import com.epam.assembler.OrderAssembler;
import com.epam.dto.request.create.MakeAnOrderRequestDTO;
import com.epam.dto.request.update.UpdateOrderRequestDTO;
import com.epam.entity.Order;
import com.epam.entity.OrderUser;
import com.epam.model.OrderModel;
import com.epam.principal.UserPrincipal;
import com.epam.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<OrderModel>> getAllOrders(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                                                                    @PathVariable int limit,
                                                                    @PathVariable int offset) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(orderAssembler.toCollectionModel(orderService.getOrders(userId, limit, offset), userId));
    }

    @GetMapping("{orderId:[0-9]+}")
    public ResponseEntity<OrderModel> getOrder(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                                               @PathVariable long orderId) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(orderAssembler.toModel(orderService.getOrder(new OrderUser(userId, orderId))));
    }

    @DeleteMapping("{orderId:[0-9]+}")
    public ResponseEntity removeOrder(@AuthenticationPrincipal final UserPrincipal userPrincipal, @PathVariable long orderId) {
        long userId = userPrincipal.getUserId();
        orderService.removeOrder(new OrderUser(userId, orderId));
        return ResponseEntity.noContent().build();
    }

    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<OrderModel> update(@RequestBody @Valid UpdateOrderRequestDTO updateOrderRequestDTO) {
        Order response = orderService.updateOrder(updateOrderRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getOrderId()).build().toUri();
        response.setUserId(updateOrderRequestDTO.getUserId());
        return ResponseEntity.created(location).body(orderAssembler.toModel(response));
    }
}
