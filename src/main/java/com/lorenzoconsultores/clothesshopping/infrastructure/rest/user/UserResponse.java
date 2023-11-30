package com.lorenzoconsultores.clothesshopping.infrastructure.rest.user;

import com.lorenzoconsultores.clothesshopping.business.domain.User;

public class UserResponse {
    private String id;
    private String name;
    private String lastName;
    private String birthDate;
    private String email;

    public UserResponse(String id, String name, String lastName, String birthDate, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getLastName(), user.getBirthDate(), user.getEmail());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }
}
