package com.getir.ordersservice.documents;

import com.getir.ordersservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private UUID id;

    private UUID userId;
    private Double totalPrice;

    private Address address;
    private List<ProductItem> products;

    private OrderStatus status = OrderStatus.RECEIVED;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastUpdatedAt = LocalDateTime.now();
}
