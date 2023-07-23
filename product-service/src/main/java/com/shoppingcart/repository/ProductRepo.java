package com.shoppingcart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shoppingcart.model.Product;

public interface ProductRepo  extends MongoRepository<Product, String> {
    
}
