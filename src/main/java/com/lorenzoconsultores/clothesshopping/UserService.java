package com.lorenzoconsultores.clothesshopping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User create (String name, String lastName, String birthDate, String email) {
        if(!isValidDateFormat(birthDate)){
           throw new InvalidUserException("Birth date must have a valid format like \"dd/MM/yyyy\"");
        }
        String id = UUID.randomUUID().toString();
        User userToCreate = new User(id, name, lastName, birthDate, email);
        userRepository.save(userToCreate);
        return userToCreate;
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
