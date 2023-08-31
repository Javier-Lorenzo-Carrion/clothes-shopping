package com.lorenzoconsultores.clothesshopping.business.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String lastName;
    private String birthDate;
    private String email;


    public User(String id, String name, String lastName, String birthDate, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public static User create(String name, String lastName, String birthDate, String email) {
        String id = UUID.randomUUID().toString();
        User user = new User(id, name, lastName, birthDate, email);
        if (!user.isValidDateFormat()) {
            throw new InvalidUserException("Birth date must have a valid format like \"dd/MM/yyyy\"");
        }
        return user;
    }

    private boolean isValidDateFormat() {
        try {
            LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
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
