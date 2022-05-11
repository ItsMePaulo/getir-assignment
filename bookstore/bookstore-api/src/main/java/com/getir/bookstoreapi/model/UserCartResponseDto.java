package com.getir.bookstoreapi.model;

import lombok.Value;

@Value
public class UserCartResponseDto {

    UserCartDto userCart;
    Boolean valid;
}
