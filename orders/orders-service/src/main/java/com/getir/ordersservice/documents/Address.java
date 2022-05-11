package com.getir.ordersservice.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    String street;
    Integer houseNumber;
    String city;
    String postalCode;
}
