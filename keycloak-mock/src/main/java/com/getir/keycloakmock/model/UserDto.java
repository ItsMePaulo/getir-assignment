package com.getir.keycloakmock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String username;
    private String email;
    private String password;

    public UserDto(String username, String email, String password) {
        this.id = null;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
