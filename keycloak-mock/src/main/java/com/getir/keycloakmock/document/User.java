package com.getir.keycloakmock.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("user")
@AllArgsConstructor
public class User {

    @Id
    private UUID id;

    @Indexed(unique=true)
    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;
}
