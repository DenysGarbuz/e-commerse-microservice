package com.denysgarbuz.microservice.repository;


import com.denysgarbuz.microservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
