package com.lorenzoconsultores.clothesshopping.business.application;

import com.lorenzoconsultores.clothesshopping.business.domain.InvalidUserException;
import com.lorenzoconsultores.clothesshopping.business.domain.User;
import com.lorenzoconsultores.clothesshopping.business.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String name, String lastName, String birthDate, String email) {
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            throw new InvalidUserException(String.format("Invalid email, %s is already in use", existingUser.getEmail()));
        });
        User userToCreate = User.create(name, lastName, birthDate, email);
        userRepository.save(userToCreate);
        return userToCreate;
    }
}
