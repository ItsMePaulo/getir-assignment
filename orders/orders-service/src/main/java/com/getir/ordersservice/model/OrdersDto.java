package com.getir.ordersservice.model;

import com.getir.ordersservice.documents.Address;
import com.getir.ordersservice.documents.ProductItem;
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

    private Address address;
    private @Valid List<ProductItem> products;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastUpdatedAt = LocalDateTime.now();

    public static OrdersDto createOrderDto(
            UUID userId,
            Double totalPrice,
            Address address,
            List<ProductItem> products
    ) {

        var order = new OrdersDto();
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        order.setAddress(address);
        order.setProducts(products);

        return order;
    }
}
