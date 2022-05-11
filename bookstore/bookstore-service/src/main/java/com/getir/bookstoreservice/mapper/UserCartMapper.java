package com.getir.bookstoreservice.mapper;

import com.getir.bookstoreapi.model.BookCartItem;
import com.getir.bookstoreapi.model.UserCartDto;
import com.getir.bookstoreservice.documents.UserCart;
import com.getir.ordersapi.model.AddressDto;
import com.getir.ordersapi.model.OrdersDto;
import com.getir.ordersapi.model.ProductItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserCartMapper {

    @Mapping(target = "totalPrice", source = "totalPrice")
    UserCart mapUserCartDtoToUserCart(UserCartDto userCartDto, Double totalPrice);

    UserCartDto mapUserCartToUserCartDto(UserCart cart);

    @Mappings({
            @Mapping(target = "userId", source = "retrievedCart.userId"),
            @Mapping(target = "totalPrice", source = "retrievedCart.totalPrice"),
            @Mapping(target = "address", source = "addressDto"),
            @Mapping(target = "products", expression = "java(mapBooksToProducts(retrievedCart.getBooks()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "lastUpdatedAt", ignore = true),
    })
    OrdersDto convertUserCartResponseToOrder(UserCart retrievedCart, AddressDto addressDto);

    default List<ProductItemDto> mapBooksToProducts(List<BookCartItem> books) {
        return books.stream().map(book -> new ProductItemDto(
                book.getBookId(), book.getAmount()
        )).collect(Collectors.toList());
    }
}
