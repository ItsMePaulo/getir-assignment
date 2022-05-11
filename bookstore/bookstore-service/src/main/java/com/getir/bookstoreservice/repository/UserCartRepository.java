package com.getir.bookstoreservice.repository;

import com.getir.bookstoreservice.documents.UserCart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserCartRepository extends MongoRepository<UserCart, UUID> {


}
