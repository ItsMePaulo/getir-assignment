package com.getir.bookstoreservice.mapper;

import com.getir.bookstoreservice.documents.UserCart;
import com.getir.bookstoreservice.model.UserCartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserCartMapper {

    @Mapping(target = "totalPrice", source = "totalPrice")
    UserCart mapUserCartDtoToUserCart(UserCartDto userCartDto, Double totalPrice);

    UserCartDto mapUserCartToUserCartDto(UserCart cart);
}
