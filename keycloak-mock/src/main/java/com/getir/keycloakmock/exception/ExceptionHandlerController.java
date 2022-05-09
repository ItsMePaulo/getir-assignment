package com.getir.keycloakmock.exception;

import com.getir.keycloakmock.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DuplicateUserException.class)
    ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateUserException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MissingUserException.class)
    ResponseEntity<ErrorResponse> handleMissingUserException(MissingUserException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }
}
