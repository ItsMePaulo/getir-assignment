package com.getir.bookstoreservice.documents;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

public class BookCartLookup {

    @Id
    UUID userId;

    String username;
    List<BookCartItem> books;
}
