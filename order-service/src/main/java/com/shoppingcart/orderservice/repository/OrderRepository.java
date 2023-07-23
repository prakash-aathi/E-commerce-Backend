package com.shoppingcart.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
    
}
