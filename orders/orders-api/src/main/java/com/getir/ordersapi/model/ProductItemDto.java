package com.getir.ordersapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemDto {

    String productId;
    @Min(value = 1, message = "Amount should be greater than 0")
    Integer amount;
}
