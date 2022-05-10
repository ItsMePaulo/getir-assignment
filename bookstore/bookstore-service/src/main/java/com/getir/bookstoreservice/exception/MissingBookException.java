package com.getir.bookstoreservice.exception;

import java.util.function.Function;

public class MissingBookException extends RuntimeException {

    private final static Function<String, String> message = (isbn) -> String.format(
            "Could not find book by ISBN number: %s in the system", isbn
    );

    public MissingBookException(String isbn) {
        super(message.apply(isbn));
    }
}
