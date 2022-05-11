package com.getir.bookstoreservice.exception;

import java.util.function.Function;

public class CouldNotCreateOrderException extends RuntimeException {

    private final static Function<String, String> message = (isbn) -> String.format(
            "Could not create order because %s: ", isbn
    );

    public CouldNotCreateOrderException(String exc) {
        super(message.apply(exc));
    }
}
