package com.getir.ordersservice.repository;

import com.getir.ordersservice.documents.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {

    @Query("{ 'lastUpdatedAt' : { $gt: ?0, $lt: ?1 }, 'status' : 'DELIVERED' }")
    List<Order> fetchGlobalCompleteOrdersBetweenDates(LocalDateTime fromDate, LocalDateTime toDate);

    @Query("{ 'userId' : ?0 , 'lastUpdatedAt' : { $gt: ?1, $lt: ?2 }, 'status' : 'DELIVERED' }")
    List<Order> fetchUserCompleteOrdersBetweenDates(UUID userId, LocalDateTime fromDate, LocalDateTime toDate);
}
