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

    public User create(CreateOrEditableUserFields fields) {
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

    public void update(String id, CreateOrEditableUserFields fields) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.update(fields);
        userRepository.save(userToUpdate);
    }

    public void delete(String id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        //userToDelete.delete(userToDelete);
        userRepository.delete(userToDelete);
    }

    public User getUser(String id) {
        User userGetted = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userGetted;
    }


    // TODO CON MIGUE: Validar update.
    // TODO PA CASA: Crear metodo delete. El metodo debe recibir un id y tal...
    // TODO PA CASA: Crear un metodo get que devuelva un usuario. Debe recibir un id.
    // TODO PA CASA: Refactor create para hacerlo con un DTO (como update)

}
