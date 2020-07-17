package com.epam.controller;

import com.epam.assembler.UserAssembler;
import com.epam.dto.request.update.UpdateUserDTO;
import com.epam.entity.User;
import com.epam.model.UserModel;
import com.epam.principal.UserPrincipal;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAssembler userAssembler;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<UserModel> getUser(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        String username = userPrincipal.getUsername();
        User response = userService.getUser(username);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.ok().location(location).body(userAssembler.toModel(response));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<UserModel>> getAllUsers(@PathVariable int limit, @PathVariable int offset) {
        List<User> responce = userService.getAll(limit, offset);
        return ResponseEntity.ok(userAssembler.toCollectionModel(responce));
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @DeleteMapping("{id:[0-9]+}")
    public ResponseEntity removeUser(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        userService.removeUser(userPrincipal.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity<UserModel> update(@RequestBody @Valid UpdateUserDTO updateUserDTO) {
        User response = userService.updateUser(updateUserDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        response.setUserId(updateUserDTO.getUserId());
        return ResponseEntity.created(location).body(userAssembler.toModel(response));
    }
}
