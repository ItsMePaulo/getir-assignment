package com.getir.ordersapi.model;

import lombok.Value;

@Value
public class StatisticsDto {

    Integer orders;
    Integer totalProducts;
    Double totalAmount;
}
