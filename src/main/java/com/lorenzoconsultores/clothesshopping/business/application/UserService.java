package com.lorenzoconsultores.clothesshopping.business.application;

import com.lorenzoconsultores.clothesshopping.business.domain.InvalidUserException;
import com.lorenzoconsultores.clothesshopping.business.domain.User;
import com.lorenzoconsultores.clothesshopping.business.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String name, String lastName, String birthDate, String email) {
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            throw new InvalidUserException(String.format("Email, %s, is already in use", existingUser.getEmail()));
        });
        User userToCreate = User.create(name, lastName, birthDate, email);
        userRepository.save(userToCreate);
        return userToCreate;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void update(String id, Optional<String> newName, Optional<String> newLastName, Optional<String> newBirthDate, Optional<String> newEmail) {
        User userToUpdate = userRepository.findById(id).get();
        newName.ifPresent(userToUpdate::setName);
        newLastName.ifPresent(userToUpdate::setLastName);
        newBirthDate.ifPresent(userToUpdate::setBirthDate);
        newEmail.ifPresent(userToUpdate::setEmail);
        userRepository.save(userToUpdate);
    }


    // TODO: Crear métodos update. El update debe recibir un id y los campos que se van a modificar. (el save ya creado sirve para este caso)
    // TODO: Crear metodo delete. El metodo debe recibir un id y tal...
    // TODO: Crear un metodo get que devuelva un usuario. Debe recibir un id.

}
