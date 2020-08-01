package com.epam.controller;

import com.epam.assembler.UserAssembler;
import com.epam.dto.request.update.updateUserRequestDTO;
import com.epam.entity.User;
import com.epam.model.UserModel;
import com.epam.principal.UserPrincipal;
import com.epam.provider.JwtTokenProvider;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private JwtTokenProvider provider;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping()
    public ResponseEntity<UserModel> getUser(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        String username = userPrincipal.getUsername();
        User response = userService.getUser(username);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.ok().location(location).body(userAssembler.toModel(response));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{id:[0-9]+}")
    public ResponseEntity<UserModel> getSomeUser(@PathVariable long id) {
        User response = userService.getUser(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.ok().location(location).body(userAssembler.toModel(response));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<CollectionModel<UserModel>> getAllUsers(@PathVariable int limit, @PathVariable int offset) {
        List<User> response = userService.getAll(limit, offset);
        return ResponseEntity.ok(userAssembler.toCollectionModel(response));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping()
    public ResponseEntity removeUser(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        userService.removeUser(userPrincipal.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id:[0-9]+}")
    public ResponseEntity removeSomeUser(@PathVariable long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(headers = {"Accept=application/json"})
    public ResponseEntity update(@RequestBody @Valid updateUserRequestDTO updateUserRequestDTO,
                                 @AuthenticationPrincipal final UserPrincipal userPrincipal) {
        User result = userService.updateUser(updateUserRequestDTO, userPrincipal);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + result.getUserId()).build().toUri();
        Map<Object, Object> response = new HashMap<>();
        response.put("result", userAssembler.toModel(result));
        String token = provider.createToken(result.getUsername());
        response.put("token", token);
        return ResponseEntity.created(location).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "{userId:[0-9]+}", headers = {"Accept=application/json"})
    public ResponseEntity<UserModel> update(@RequestBody @Valid updateUserRequestDTO updateUserRequestDTO,
                                            @PathVariable long userId) {
        User response = userService.updateUser(updateUserRequestDTO, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.created(location).body(userAssembler.toModel(response));
    }
}
