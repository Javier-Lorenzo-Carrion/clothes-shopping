package com.lorenzoconsultores.clothesshopping.business.application;

import com.lorenzoconsultores.clothesshopping.business.domain.InvalidUserException;
import com.lorenzoconsultores.clothesshopping.business.domain.User;
import com.lorenzoconsultores.clothesshopping.business.domain.UserRepository;
import com.lorenzoconsultores.clothesshopping.business.domain.UserToUpdate;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void update(String id, UserToUpdate userToUpdate) {
        User foundUser = userRepository.findById(id).get();
        foundUser.update(userToUpdate);
        userRepository.save(foundUser);
    }


    // TODO: Crear métodos update. El update debe recibir un id y los campos que se van a modificar. (el save ya creado sirve para este caso)
    // TODO: Crear metodo delete. El metodo debe recibir un id y tal...
    // TODO: Crear un metodo get que devuelva un usuario. Debe recibir un id.

}
