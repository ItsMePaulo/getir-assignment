package com.getir.bookstoreservice.repository;

import com.getir.bookstoreservice.documents.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
