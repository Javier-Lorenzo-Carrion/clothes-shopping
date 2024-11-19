package com.lorenzoconsultores.clothesshopping.infrastructure.rest.user;

import com.lorenzoconsultores.clothesshopping.business.domain.User;

public record UserResponse(String id, String name, String lastName, String birthDate, String email) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getLastName(), user.getBirthDate(), user.getEmail());
    }
}
