package com.lorenzoconsultores.clothesshopping.infrastructure.persistence.jpa;

import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJPARepository extends JpaRepository<UserEntity, UUID> {


    Optional<UserEntity> findByEmail(String mail);

    Optional<UserEntity> findById(String id);

}
