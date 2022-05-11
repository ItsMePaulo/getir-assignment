package com.getir.ordersservice.repository;

import com.getir.ordersservice.documents.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {

}
