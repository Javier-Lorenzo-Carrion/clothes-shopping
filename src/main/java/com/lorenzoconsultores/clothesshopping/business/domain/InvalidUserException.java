package com.lorenzoconsultores.clothesshopping.business.domain;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException (String message){
        super(message);
    }
}
