package com.lorenzoconsultores.clothesshopping.business.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    void save(User user);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    Optional<User> findById(String id);

    void delete(User user);
}
