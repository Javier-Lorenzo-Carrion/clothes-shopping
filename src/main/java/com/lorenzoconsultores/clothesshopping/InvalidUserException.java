package com.lorenzoconsultores.clothesshopping;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException (String message){
        super(message);
    }
}
