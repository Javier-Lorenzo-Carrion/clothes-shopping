package com.lorenzoconsultores.clothesshopping.infrastructure.persistence.postgres;

import com.lorenzoconsultores.clothesshopping.business.domain.User;
import com.lorenzoconsultores.clothesshopping.business.domain.UserRepository;
import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.entities.UserEntity;
import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.jpa.UserJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostgreUserRepository implements UserRepository {

    private final UserJPARepository userJPARepository;

    public PostgreUserRepository(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public void save(User user) {
        userJPARepository.save(UserEntity.fromUser(user));

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJPARepository.findByEmail(email).map(UserEntity::toUser);
    }

    @Override
    public List<User> findAll() {
        return userJPARepository.findAll().stream().map(UserEntity::toUser).toList();
    }
}
