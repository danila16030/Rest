package com.epam.controller;

import com.epam.assembler.CustomerAssembler;
import com.epam.entity.Customer;
import com.epam.model.CustomerModel;
import com.epam.principal.UserPrincipal;
import com.epam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
    @Autowired
    private CustomerAssembler customerAssembler;
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public ResponseEntity<CustomerModel> getCustomer(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        long id = userPrincipal.getUserId();
        Customer response = customerService.getUser(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.ok().location(location).body(customerAssembler.toCustomerModel(response, userPrincipal));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{userId:[0-9]+}")
    public ResponseEntity<CustomerModel> getCustomer(@PathVariable long userId, @AuthenticationPrincipal final
    UserPrincipal userPrincipal) {
        Customer response = customerService.getUser(userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.ok().location(location).body(customerAssembler.toCustomerModel(response, userPrincipal));
    }

    @GetMapping("/top")
    public ResponseEntity<CustomerModel> getTopUser(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        return ResponseEntity.ok(customerAssembler.toCustomerModel(customerService.getTopCustomer(),userPrincipal));
    }
}
