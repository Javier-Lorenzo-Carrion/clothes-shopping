package com.lorenzoconsultores.clothesshopping.infrastructure.rest.user;

import com.lorenzoconsultores.clothesshopping.business.application.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateUserRequest createUserRequest) {
        userService.create(createUserRequest.getName(), createUserRequest.getLastName(), createUserRequest.getBirthDate(), createUserRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll().stream().map(UserResponse::fromUser).toList());
    }
}
