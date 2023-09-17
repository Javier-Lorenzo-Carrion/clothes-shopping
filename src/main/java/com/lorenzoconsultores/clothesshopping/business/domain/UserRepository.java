package com.lorenzoconsultores.clothesshopping.business.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    void save(User user);

}
