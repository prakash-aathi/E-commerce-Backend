package com.ecommerce.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.inventory.model.Inventorymodel;

public interface InventoryRepository extends JpaRepository<Inventorymodel, String> {

    List<Inventorymodel> findBySkucodeIn (List<String> skuCode);
     
}
