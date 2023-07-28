package com.lorenzoconsultores.clothesshopping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class UserService {

    public User create (String name, String lastName, String birthDate, String email) {
        if(!isValidDateFormat(birthDate)){
           throw new InvalidUserException("Birth date must have a valid format like \"dd/MM/yyyy\"");
        }
        String id = UUID.randomUUID().toString();
        return new User(id, name, lastName, birthDate, email);
    }

    private boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
