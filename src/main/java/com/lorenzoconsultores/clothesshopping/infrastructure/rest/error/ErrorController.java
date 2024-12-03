package com.lorenzoconsultores.clothesshopping.infrastructure.rest.error;

import com.lorenzoconsultores.clothesshopping.business.domain.InvalidUserException;
import com.lorenzoconsultores.clothesshopping.business.domain.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {InvalidUserException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<ErrorResponse> handleUnknownException(RuntimeException exception) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("Unknown error"));
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }


}
