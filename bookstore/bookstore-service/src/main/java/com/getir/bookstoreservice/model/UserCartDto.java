package com.getir.bookstoreservice.model;

import com.getir.bookstoreservice.documents.BookCartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCartDto {

    UUID userId;
    String username;
    @Valid List<BookCartItem> books;

    Double totalPrice;
}
