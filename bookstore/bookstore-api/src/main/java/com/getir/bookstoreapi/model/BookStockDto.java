package com.getir.bookstoreapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookStockDto {

    BookStockMethod method;
    Integer amount;

    public Integer performMappingMethod(Integer stock) {
        return method.performOperation(amount, stock);
    }
}
