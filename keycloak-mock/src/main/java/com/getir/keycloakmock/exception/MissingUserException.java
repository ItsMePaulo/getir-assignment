package com.getir.keycloakmock.exception;

import java.util.UUID;
import java.util.function.Function;

public class MissingUserException extends RuntimeException {

    private final static Function<UUID, String> message = id -> String.format(
            "Could not find user: %s withing the system", id
    );

    public MissingUserException(UUID id) {
        super(message.apply(id));
    }
}
