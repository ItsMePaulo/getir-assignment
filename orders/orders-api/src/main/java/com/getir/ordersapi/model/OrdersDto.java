package com.getir.ordersapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {
    private UUID id;
    private UUID userId;
    private Double totalPrice;
    private OrderStatus status;

    private AddressDto address;
    private @Valid List<ProductItemDto> products;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastUpdatedAt = LocalDateTime.now();

    public static OrdersDto createOrderDto(
            UUID userId,
            Double totalPrice,
            AddressDto address,
            List<ProductItemDto> products
    ) {

        var order = new OrdersDto();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setAddress(address);
        order.setProducts(products);

        return order;
    }
}
