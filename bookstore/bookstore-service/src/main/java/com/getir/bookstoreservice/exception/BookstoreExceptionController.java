package com.getir.bookstoreservice.exception;

import com.getir.common.exceptions.DuplicateUniqueKeyException;
import com.getir.common.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookstoreExceptionController {

    @ExceptionHandler(DuplicateUniqueKeyException.class)
    ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateUniqueKeyException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MissingBookException.class)
    ResponseEntity<ErrorResponse> handleMissingBookException(MissingBookException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(BooksOutOfStockException.class)
    ResponseEntity<ErrorResponse> handleBooksOutOfStockException(BooksOutOfStockException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        // TODO: Add some sanitation for this error message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getLocalizedMessage()));
    }
}
