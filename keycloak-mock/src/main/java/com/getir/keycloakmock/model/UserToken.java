package com.getir.keycloakmock.model;

import lombok.Value;

import java.util.UUID;

@Value
public class UserToken {

    UUID id;
    String username;
}
