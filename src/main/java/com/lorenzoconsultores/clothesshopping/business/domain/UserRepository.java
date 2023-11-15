package com.lorenzoconsultores.clothesshopping.business.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    void save(User user);

    public Optional<User> findByEmail(String email);

}
