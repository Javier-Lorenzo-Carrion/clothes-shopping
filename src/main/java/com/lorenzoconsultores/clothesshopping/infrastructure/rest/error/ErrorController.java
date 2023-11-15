package com.lorenzoconsultores.clothesshopping.infrastructure.rest.error;

import com.lorenzoconsultores.clothesshopping.business.domain.InvalidUserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {InvalidUserException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException exception, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<ErrorResponse> handleUnknownException(RuntimeException exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("Unknown error"));
    }

}
