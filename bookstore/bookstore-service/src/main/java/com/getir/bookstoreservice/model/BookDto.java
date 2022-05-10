package com.getir.bookstoreservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String isbn;
    private String title;
    private String subTitle;
    private int quantity;
    private double price;
}
