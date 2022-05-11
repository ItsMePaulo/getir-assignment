package com.getir.bookstoreservice.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCartItem {
    private String bookId;

    @Min(value = 1, message = "You must pass in an amount greater than 0")
    private int amount;
}
