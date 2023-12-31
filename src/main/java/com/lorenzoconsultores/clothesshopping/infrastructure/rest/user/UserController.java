package com.lorenzoconsultores.clothesshopping.infrastructure.rest.user;

import com.lorenzoconsultores.clothesshopping.business.application.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
