package com.epam.controller;

import com.epam.dto.request.create.CreateUserDTO;
import com.epam.dto.responce.UserResponseDTO;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("{id:[0-9]+}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {
        UserResponseDTO response = userService.getUser(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.ok().location(location).body(response);
    }

    @GetMapping("{limit:[0-9]+},{offset:[0-9]+}")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@PathVariable int limit, @PathVariable int offset) {
        return ResponseEntity.ok(userService.getAll(limit, offset));
    }

    @PostMapping(value = "/user", headers = {"Accept=application/json"})
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid CreateUserDTO userDTO) {
        UserResponseDTO response = userService.createUser(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + response.getUserId()).build().toUri();
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("{id:[0-9]+}")
    public ResponseEntity removeUser(@PathVariable long id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer")
    public ResponseEntity getTopUser() {
        return ResponseEntity.ok(userService.getTopUser());
    }
}
