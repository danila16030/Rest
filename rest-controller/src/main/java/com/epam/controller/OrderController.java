package com.epam.controller;

import com.epam.assembler.OrderAssembler;
import com.epam.dto.request.create.MakeAnOrdersRequestDto;
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
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAssembler orderAssembler;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping(headers = {"Accept=application/json"})
    public ResponseEntity<CollectionModel<OrderModel>> makeAnOrder(@RequestBody @Valid MakeAnOrdersRequestDto makeAnOrderRequestDTO,
                                                                   @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        List<Order> response = orderService.makeAnOrder(makeAnOrderRequestDTO, userPrincipal.getUserId());
        for (Order order : response) {
            order.setUserId(userPrincipal.getUserId());
        }
        return ResponseEntity.ok().body(orderAssembler.toCollectionModel(response));
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<CollectionModel<OrderModel>> getAllOrders(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                                                                    @RequestParam(defaultValue = "10") int limit,
                                                                    @RequestParam(defaultValue = "0") int offset) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(orderAssembler.toCollectionModel(orderService.getOrders(userId, limit, offset),
                userPrincipal));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{userId:[0-9]+},{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<OrderModel>> getAllUserOrders(@PathVariable int userId,
                                                                        @PathVariable int limit,
                                                                        @PathVariable int offset,
                                                                        @AuthenticationPrincipal final
                                                                        UserPrincipal userPrincipal) {
        return ResponseEntity.ok(orderAssembler.toCollectionModel(orderService.getOrders(userId, limit, offset),
                userPrincipal));
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("{orderId:[0-9]+}")
    public ResponseEntity<OrderModel> getOrder(@AuthenticationPrincipal final UserPrincipal userPrincipal,
                                               @PathVariable long orderId) {
        long userId = userPrincipal.getUserId();
        return ResponseEntity.ok(orderAssembler.toModel(orderService.getOrder(new OrderUser(userId, orderId))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{orderId:[0-9]+},{userId:[0-9]+}")
    public ResponseEntity<OrderModel> getUserOrder(@PathVariable long userId,
                                                   @PathVariable long orderId) {
        return ResponseEntity.ok(orderAssembler.toModel(orderService.getOrder(new OrderUser(userId, orderId))));
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @DeleteMapping("{orderId:[0-9]+}")
    public ResponseEntity removeOrder(@AuthenticationPrincipal final UserPrincipal userPrincipal, @PathVariable long orderId) {
        long userId = userPrincipal.getUserId();
        orderService.removeOrder(new OrderUser(userId, orderId));
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{orderId:[0-9]+},{userId:[0-9]+}")
    public ResponseEntity removeUserOrder(@PathVariable long orderId, @PathVariable long userId) {
        orderService.removeOrder(new OrderUser(userId, orderId));
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<OrderModel> updateOrder(@RequestBody @Valid UpdateOrderRequestDTO updateOrderRequestDTO,
                                                  @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        Order response = orderService.updateOrder(updateOrderRequestDTO, userPrincipal.getUserId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getOrderId()).build().toUri();
        response.setUserId(userPrincipal.getUserId());
        return ResponseEntity.created(location).body(orderAssembler.toModel(response));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "{userId:[0-9]+}", headers = {"Accept=application/json"})
    public ResponseEntity<OrderModel> updateUserOrder(@RequestBody @Valid UpdateOrderRequestDTO updateOrderRequestDTO,
                                                      @PathVariable long userId) {
        Order response = orderService.updateOrder(updateOrderRequestDTO, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getOrderId()).build().toUri();
        response.setUserId(userId);
        return ResponseEntity.created(location).body(orderAssembler.toModel(response));
    }
}
