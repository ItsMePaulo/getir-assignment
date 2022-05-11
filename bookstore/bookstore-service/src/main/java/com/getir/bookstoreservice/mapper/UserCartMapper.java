package com.getir.bookstoreservice.mapper;

import com.getir.bookstoreservice.documents.UserCart;
import com.getir.bookstoreservice.model.UserCartDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCartMapper {

    UserCart mapUserCartDtoToUserCart(UserCartDto userCartDto);
}
