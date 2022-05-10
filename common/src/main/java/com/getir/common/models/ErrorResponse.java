package com.getir.common.models;

import lombok.Value;

@Value
public class ErrorResponse {

    boolean succeeded = false;
    String message;
}
