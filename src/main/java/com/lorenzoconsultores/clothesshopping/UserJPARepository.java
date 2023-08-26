package com.lorenzoconsultores.clothesshopping;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJPARepository extends JpaRepository <UserEntity, UUID>{


}
