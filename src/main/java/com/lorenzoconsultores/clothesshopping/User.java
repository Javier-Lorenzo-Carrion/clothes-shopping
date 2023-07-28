package com.lorenzoconsultores.clothesshopping;

public class User {
    private String id;
    private String name;
    private String lastName;
    private String birthDate;
    private String email;

    public User(String id, String name, String lastName, String birthDate, String email){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
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
