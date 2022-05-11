package com.getir.bookstoreservice.model;

import lombok.Value;

import java.util.List;

@Value
public class UserCartResponseDto {

    UserCartDto userCart;
    Boolean valid;
}
