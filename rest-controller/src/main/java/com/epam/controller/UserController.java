package com.epam.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.epam.assembler.UserAssembler;
import com.epam.dto.request.update.UpdateUserRequestDTO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private JwtTokenProvider provider;

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<UserModel> getUser(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        String username = userPrincipal.getUsername();
        User response = userService.getUser(username);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build()
                .toUri();
        return ResponseEntity.ok().location(location).body(userAssembler.toModel(response));
    }

    @PreAuthorize(" hasAuthority('ADMIN')")
    @GetMapping(value = "{username}")
    public ResponseEntity<UserModel> getSomeUserByName(@AuthenticationPrincipal final UserPrincipal userPrincipal,
            @PathVariable String username) {
        User response = userService.getUser(username);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build()
                .toUri();
        return ResponseEntity.ok().location(location).body(userAssembler.toModel(response));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<CollectionModel<UserModel>> getAllUsers(@RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        List<User> response = userService.getAll(limit, offset);
        return ResponseEntity.ok(userAssembler.toCollectionModel(response));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all/{username}")
    public ResponseEntity<CollectionModel<UserModel>> getAllUsersByPartialName(
            @PathVariable String username,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        List<User> response = userService.getAllByPartialName(username, limit, offset);
        return ResponseEntity.ok(userAssembler.toCollectionModel(response));
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @DeleteMapping()
    public ResponseEntity removeUser(@AuthenticationPrincipal final UserPrincipal userPrincipal) {
        userService.removeUser(userPrincipal.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @DeleteMapping("{id:[0-9]+}")
    public ResponseEntity removeSomeUser(@PathVariable long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PutMapping(headers = { "Accept=application/json" })
    public ResponseEntity update(@RequestBody @Valid UpdateUserRequestDTO updateUserRequestDTO,
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
    @PutMapping(value = "{userId:[0-9]+}", headers = { "Accept=application/json" })
    public ResponseEntity<UserModel> update(@RequestBody @Valid UpdateUserRequestDTO updateUserRequestDTO,
            @PathVariable long userId) {
        User response = userService.updateUser(updateUserRequestDTO, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build()
                .toUri();
        return ResponseEntity.created(location).body(userAssembler.toModel(response));
    }
}
