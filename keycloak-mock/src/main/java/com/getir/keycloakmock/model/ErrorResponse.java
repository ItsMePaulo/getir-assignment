package com.getir.keycloakmock.model;

import lombok.Value;

@Value
public class ErrorResponse {

    boolean succeeded = false;
    String message;
}
