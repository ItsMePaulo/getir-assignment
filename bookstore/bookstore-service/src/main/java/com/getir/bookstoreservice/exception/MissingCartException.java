package com.getir.bookstoreservice.exception;

import java.util.UUID;
import java.util.function.Function;

public class MissingCartException extends RuntimeException {

    private final static Function<UUID, String> message = (id) -> String.format(
            "Missing cart, could not find cart for user: %s in the system", id
    );

    public MissingCartException(UUID id) {
        super(message.apply(id));
    }
}
