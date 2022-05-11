package com.getir.ordersservice.model;

import lombok.Value;

@Value
public class StatisticsDto {

    Integer orders;
    Integer totalProducts;
    Double totalAmount;
}
