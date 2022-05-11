package com.getir.ordersservice.exception;

import java.util.UUID;
import java.util.function.Function;

public class MissingOrderException extends RuntimeException {

    private final static Function<UUID, String> message = (id) -> String.format(
            "Could not find order by id: %s in the system", id
    );

    public MissingOrderException(UUID id) {
        super(message.apply(id));
    }
}
