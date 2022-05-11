package com.getir.ordersapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    String street;
    Integer houseNumber;
    String city;
    String postalCode;
}