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

    @GetMapping()
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<CustomerModel> getCustomer(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        long id = userPrincipal.getUserId();
        Customer response = customerService.getUser(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.ok().location(location).body(customerAssembler.toModel(response));
    }

    @GetMapping("/top")
    public ResponseEntity<CustomerModel> getTopUser() {
        return ResponseEntity.ok(customerAssembler.toModel(customerService.getTopCustomer()));
    }
}
