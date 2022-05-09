package com.getir.keycloakmock.repository;


import com.getir.keycloakmock.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
}
