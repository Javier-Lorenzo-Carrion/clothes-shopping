package com.lorenzoconsultores.clothesshopping.infrastructure.persistence.entities;

import com.lorenzoconsultores.clothesshopping.business.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "birthDate")
    private String birthDate;
    @Column(name = "email")
    private String email;

    public UserEntity(){}
    public UserEntity(UUID id, String name, String lastName, String birthDate, String email){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserEntity fromUser(User user){
        return new UserEntity(UUID.fromString(user.getId()), user.getName(), user.getLastName(), user.getBirthDate(), user.getEmail());
    }
}
