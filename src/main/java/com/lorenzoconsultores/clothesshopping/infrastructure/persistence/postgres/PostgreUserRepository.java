package com.lorenzoconsultores.clothesshopping.infrastructure.persistence.postgres;

import com.lorenzoconsultores.clothesshopping.business.domain.UserRepository;
import com.lorenzoconsultores.clothesshopping.business.domain.User;
import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.entities.UserEntity;
import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.jpa.UserJPARepository;
import org.springframework.stereotype.Component;

@Component
public class PostgreUserRepository implements UserRepository {

    private final UserJPARepository userJPARepository;

    public PostgreUserRepository(UserJPARepository userJPARepository){
        this.userJPARepository = userJPARepository;
    }

    @Override
    public void save(User user) {
        userJPARepository.save(UserEntity.fromUser(user));

    }
}
