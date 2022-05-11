package com.getir.bookstoreservice.documents;

import com.getir.bookstoreapi.model.BookCartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class UserCart {

    @Id
    private UUID userId;

    private String username;
    private List<BookCartItem> books;
    private Double totalPrice;
}
