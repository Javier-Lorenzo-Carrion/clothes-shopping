package com.lorenzoconsultores.clothesshopping.business.application;

import com.lorenzoconsultores.clothesshopping.business.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(CreatableUserFields fields) {
        userRepository.findByEmail(fields.email()).ifPresent(existingUser -> {
            throw new InvalidUserException(String.format("Email, %s, is already in use", existingUser.getEmail()));
        });
        User userToCreate = User.create(fields);
        userRepository.save(userToCreate);
        return userToCreate;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void update(String id, EditableUserFields fields) {
        User userToUpdate = get(id);
        userToUpdate.update(fields);
        userRepository.save(userToUpdate);
    }

    public void delete(String id) {
        User userToDelete = get(id);
        userRepository.delete(userToDelete);
    }

    public User get(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
