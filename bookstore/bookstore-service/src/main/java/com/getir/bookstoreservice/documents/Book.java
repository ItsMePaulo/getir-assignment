package com.getir.bookstoreservice.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    String isbn;

    String title;
    String subTitle;
    Integer stock;
    Double price;
}
