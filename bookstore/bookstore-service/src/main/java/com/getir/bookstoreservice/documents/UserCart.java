package com.getir.bookstoreservice.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
public class UserCart {

    @Id
    UUID userId;

    String username;
    List<BookCartItem> books;
}
