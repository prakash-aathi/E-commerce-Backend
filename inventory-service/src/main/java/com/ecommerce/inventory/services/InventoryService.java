package com.ecommerce.inventory.services;


import com.ecommerce.inventory.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.inventory.repository.InventoryRepository;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional( readOnly = true )
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkucodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkucode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                        ).toList();
    }
    
    
    
}
